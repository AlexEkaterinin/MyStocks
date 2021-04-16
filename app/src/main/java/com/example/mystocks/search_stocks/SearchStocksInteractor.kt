package com.example.mystocks

import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SearchStocksInteractor {

    fun getSearchedStock(symbol: String): Single<List<StockModel>>

    fun getDefaultStocksList(): Single<List<StockModel>>

    fun checkAvailableSearchedStocks(symbol: String): Single<Boolean>
}

class SearchStocksInteractorImpl @Inject constructor(
    private val repository: StockInfoRepository,
    private val mapper: StockMapper
) : SearchStocksInteractor {

    override fun checkAvailableSearchedStocks(symbol: String): Single<Boolean> =
        getSearchedStock(symbol)
            .map { responseList ->
                isCorrectResponseStocks(responseList)
            }

    override fun getSearchedStock(symbol: String): Single<List<StockModel>> =
        Single.zip(
            repository.getSpecificStocksList(symbol).subscribeOn(Schedulers.io()),
            repository.getAllFavoriteLocalStocks().subscribeOn(Schedulers.io()),
            { responseList, savedFavoriteList ->
                checkFavoriteStocks(responseList, savedFavoriteList)
                responseList.toList()
                responseList.map(mapper::fromResponseToStockModel)
            }
        )

    override fun getDefaultStocksList(): Single<List<StockModel>> =
        Single.zip(
            repository.getDowJonesList().subscribeOn(Schedulers.io()),
            repository.getAllFavoriteLocalStocks().subscribeOn(Schedulers.io()),
            { responseList, savedFavoriteList ->
                checkFavoriteStocks(responseList, savedFavoriteList)
                responseList.toList()
                responseList.map(mapper::fromResponseToStockModel)
            }
        )

    private fun isCorrectResponseStocks(stocksList: List<StockModel>): Boolean {
        return !(stocksList.first().price == null || stocksList.first().currency == null || stocksList.first().change == null || stocksList.isEmpty())
    }

    private fun checkFavoriteStocks(
        responseList: List<StocksInfoResponseType>,
        localFavoriteList: List<String>
    ) {
        responseList.toMutableList().forEach { stock ->
            if (localFavoriteList.contains(stock.symbol)) {
                stock.isFavorite = true
            }
        }
    }
}