package com.example.mystocks

import com.example.mystocks.api.StocksApi
import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.db.FavoriteStocksDao
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface StockInfoInteractor {

    fun getAllFavoriteLocalStocks(): Single<List<String>>

    fun changeFavorite(stock: StockModel): Completable

    fun insertFavoriteStock(stock: StockModel): Completable

    fun removeFavoriteStock(stock: StockModel): Completable

    fun getSearchedStock(symbol: String): Single<List<StocksInfoResponseType>>

    fun getDowJonesList(): Single<List<StocksInfoResponseType>>

    fun getDefaultStocksList(): Single<List<StocksInfoResponseType>>

    fun getFavoriteStocksList(): Single<List<StocksInfoResponseType>>

    fun checkAvailableFavoriteStocks(): Single<Boolean>

    fun checkAvailableSearchedStocks(symbol: String): Single<Boolean>

    fun isCorrectResponseStocks(stocksList: List<StocksInfoResponseType>): Boolean
}

class StockInfoInteractorImpl @Inject constructor(
    private val dao: FavoriteStocksDao,
    private val api: StocksApi,
    private val mapper: StockMapper
) : StockInfoInteractor {

    override fun changeFavorite(stock: StockModel): Completable =
        if (stock.isFavorite) {
            removeFavoriteStock(stock)
        } else {
            insertFavoriteStock(stock)
        }

    override fun checkAvailableFavoriteStocks(): Single<Boolean> =
        getAllFavoriteLocalStocks()
            .map { favoriteList ->
                favoriteList.isEmpty()
            }

    override fun checkAvailableSearchedStocks(symbol: String): Single<Boolean> =
        getSearchedStock(symbol)
            .map { responseList ->
                isCorrectResponseStocks(responseList)
            }

    override fun isCorrectResponseStocks(stocksList: List<StocksInfoResponseType>): Boolean {
        return !(stocksList.first().price == null || stocksList.first().currency == null || stocksList.first().change == null || stocksList.isEmpty())
    }

    override fun insertFavoriteStock(stock: StockModel): Completable =
        Completable.create { emitter ->
            dao.insertFavoriteStock(mapper.fromModelToEntity(stock))
            emitter.onComplete()
        }

    override fun removeFavoriteStock(stock: StockModel): Completable =
        Completable.create { emitter ->
            dao.removeFavoriteStock(stock.symbol)
            emitter.onComplete()
        }

    override fun getSearchedStock(symbol: String): Single<List<StocksInfoResponseType>> =
        Single.zip(
            api.getSpecificStocksList(symbol).subscribeOn(Schedulers.io()),
            getAllFavoriteLocalStocks().subscribeOn(Schedulers.io()),
            { responseList, savedFavoriteList ->
                responseList.toMutableList().forEach { stock ->
                    if (savedFavoriteList.contains(stock.symbol)) {
                        stock.isFavorite = true
                    }
                }
                responseList.toList()
            }
        )

    override fun getDefaultStocksList(): Single<List<StocksInfoResponseType>> =
        Single.zip(
            getDowJonesList().subscribeOn(Schedulers.io()),
            getAllFavoriteLocalStocks().subscribeOn(Schedulers.io()),
            { responseList, savedFavoriteList ->
                responseList.toMutableList().forEach { stock ->
                    if (savedFavoriteList.contains(stock.symbol)) {
                        stock.isFavorite = true
                    }
                }
                responseList.toList()
            }
        )

    override fun getFavoriteStocksList(): Single<List<StocksInfoResponseType>> =
        getAllFavoriteLocalStocks()
            .subscribeOn(Schedulers.io())
            .flatMap { favoriteList ->
                val apiPath = favoriteList.toMutableList().joinToString(",")
                api.getSpecificStocksList(apiPath)
            }
            .map { responseList ->
                responseList.toMutableList().forEach { stock ->
                    stock.isFavorite = true
                }
                responseList.toList()
            }

    override fun getDowJonesList(): Single<List<StocksInfoResponseType>> =
        api.getDowJonesList()

    override fun getAllFavoriteLocalStocks(): Single<List<String>> =
        Single.fromCallable {
            dao.getAllFavoriteStocks()
        }
}