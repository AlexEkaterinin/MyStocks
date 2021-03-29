package com.example.mystocks

import com.example.mystocks.api.StocksApi
import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.db.FavoriteStocksDao
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface StockInfoRepository {

    fun changeFavorite(stock: StockModel): Completable

    fun getAllFavoriteLocalStocks(): Single<List<String>>

    fun getSpecificStocksList(symbol: String): Single<List<StocksInfoResponseType>>

    fun getDowJonesList(): Single<List<StocksInfoResponseType>>
}

class StocksInfoRepositoryImpl @Inject constructor(
    private val dao: FavoriteStocksDao,
    private val api: StocksApi,
    private val mapper: StockMapper
): StockInfoRepository {

    override fun changeFavorite(stock: StockModel): Completable =
        if (stock.isFavorite) {
            removeFavoriteStock(stock)
        } else {
            insertFavoriteStock(stock)
        }

    override fun getAllFavoriteLocalStocks(): Single<List<String>> =
        Single.fromCallable {
            dao.getAllFavoriteStocks()
        }

    override fun getSpecificStocksList(symbol: String): Single<List<StocksInfoResponseType>> =
        api.getSpecificStocksList(symbol)

    override fun getDowJonesList(): Single<List<StocksInfoResponseType>> =
        api.getDowJonesList()

    private fun insertFavoriteStock(stock: StockModel): Completable =
        Completable.create { emitter ->
            dao.insertFavoriteStock(mapper.fromModelToEntity(stock))
            emitter.onComplete()
        }

    private fun removeFavoriteStock(stock: StockModel): Completable =
        Completable.create { emitter ->
            dao.removeFavoriteStock(stock.symbol)
            emitter.onComplete()
        }
}