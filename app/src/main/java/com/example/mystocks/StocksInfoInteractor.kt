package com.example.mystocks

import com.example.mystocks.api.StocksApi
import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.db.FavoriteStocksDao
import com.example.mystocks.db.FavoriteStocksEntity
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

    fun getDowJonesList(): Single<List<StocksInfoResponseType>>

    fun getDefaultStocksList(): Single<List<StocksInfoResponseType>>

    fun getFavoriteStocksList(): Single<List<StocksInfoResponseType>>

    fun checkAvailableFavoriteStocks(): Single<Boolean>
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
            .subscribeOn(Schedulers.io())
            .map { favoriteList ->
                favoriteList.isEmpty()
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
                api.getFavoriteStocksList(apiPath)
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