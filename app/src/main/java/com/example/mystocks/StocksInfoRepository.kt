package com.example.mystocks

import com.example.mystocks.api.CompanyInfoResponseType
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

    fun getCompanyProfileInfo(symbol: String?): Single<CompanyInfoResponseType>

    fun insertFavoriteStock(symbol: String?): Completable

    fun removeFavoriteStock(symbol: String?): Completable
}

class StocksInfoRepositoryImpl @Inject constructor(
    private val dao: FavoriteStocksDao,
    private val api: StocksApi,
    private val mapper: StockMapper
) : StockInfoRepository {

    override fun changeFavorite(stock: StockModel): Completable =
        if (stock.isFavorite) {
            removeFavoriteStock(stock.symbol)
        } else {
            insertFavoriteStock(stock.symbol)
        }

    override fun getAllFavoriteLocalStocks(): Single<List<String>> =
        Single.fromCallable {
            dao.getAllFavoriteStocks()
        }

    override fun getSpecificStocksList(symbol: String): Single<List<StocksInfoResponseType>> =
        api.getSpecificStocksList(symbol)

    override fun getDowJonesList(): Single<List<StocksInfoResponseType>> =
        api.getDowJonesList()

    override fun getCompanyProfileInfo(symbol: String?): Single<CompanyInfoResponseType> =
        api.getCompanyProfileInfo(symbol)

    override fun insertFavoriteStock(symbol: String?): Completable =
        Completable.create { emitter ->
            dao.insertFavoriteStock(mapper.fromModelToStockEntity(symbol))
            emitter.onComplete()
        }

    override fun removeFavoriteStock(symbol: String?): Completable =
        Completable.create { emitter ->
            dao.removeFavoriteStock(symbol)
            emitter.onComplete()
        }
}