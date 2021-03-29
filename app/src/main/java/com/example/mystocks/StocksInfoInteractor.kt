package com.example.mystocks

import com.example.mystocks.api.CompanyInfoResponseType
import com.example.mystocks.api.StocksInfoResponseType
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface StockInfoInteractor {

    fun getSearchedStock(symbol: String): Single<List<StocksInfoResponseType>>

    fun getDefaultStocksList(): Single<List<StocksInfoResponseType>>

    fun getFavoriteStocksList(): Single<List<StocksInfoResponseType>>

    fun checkAvailableFavoriteStocks(): Single<Boolean>

    fun checkAvailableSearchedStocks(symbol: String): Single<Boolean>
}

class StockInfoInteractorImpl @Inject constructor(
    private val repository: StockInfoRepository
) : StockInfoInteractor {

    override fun checkAvailableFavoriteStocks(): Single<Boolean> =
        repository.getAllFavoriteLocalStocks()
            .map { favoriteList ->
                favoriteList.isEmpty()
            }

    override fun checkAvailableSearchedStocks(symbol: String): Single<Boolean> =
        getSearchedStock(symbol)
            .map { responseList ->
                isCorrectResponseStocks(responseList)
            }

    override fun getSearchedStock(symbol: String): Single<List<StocksInfoResponseType>> =
        Single.zip(
            repository.getSpecificStocksList(symbol).subscribeOn(Schedulers.io()),
            repository.getAllFavoriteLocalStocks().subscribeOn(Schedulers.io()),
            { responseList, savedFavoriteList ->
                checkFavoriteStocks(responseList, savedFavoriteList)
                responseList.toList()
            }
        )

    override fun getDefaultStocksList(): Single<List<StocksInfoResponseType>> =
        Single.zip(
            repository.getDowJonesList().subscribeOn(Schedulers.io()),
            repository.getAllFavoriteLocalStocks().subscribeOn(Schedulers.io()),
            { responseList, savedFavoriteList ->
                checkFavoriteStocks(responseList, savedFavoriteList)
                responseList.toList()
            }
        )

    override fun getFavoriteStocksList(): Single<List<StocksInfoResponseType>> =
        repository.getAllFavoriteLocalStocks()
            .subscribeOn(Schedulers.io())
            .flatMap { favoriteList ->
                val apiPath = favoriteList.toMutableList().joinToString(",")
                repository.getSpecificStocksList(apiPath)
            }
            .map { responseList ->
                responseList.toMutableList().forEach { stock ->
                    stock.isFavorite = true
                }
                responseList.toList()
            }

    private fun isCorrectResponseStocks(stocksList: List<StocksInfoResponseType>): Boolean {
        return !(stocksList.first().price == null || stocksList.first().currency == null || stocksList.first().change == null || stocksList.isEmpty())
    }

    private fun checkFavoriteStocks(responseList: List<StocksInfoResponseType>, localFavoriteList: List<String>) {
        responseList.toMutableList().forEach { stock ->
            if (localFavoriteList.contains(stock.symbol)) {
                stock.isFavorite = true
            }
        }
    }
}