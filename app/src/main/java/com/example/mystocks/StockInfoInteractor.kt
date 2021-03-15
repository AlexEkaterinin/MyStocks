package com.example.mystocks

import com.example.mystocks.api.StocksApi
import com.example.mystocks.db.FavoriteStockEntity
import com.example.mystocks.db.FavoriteStocksDatabase
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.Single

interface StockInfoInteractor {
    //    fun getFavoritesStocks(stock: StockModel): Single<List<String>>
    fun insertFavoriteStock(stock: StockModel): Single<Unit>
}

class StockInfoInteractorImpl(
    private val db: FavoriteStocksDatabase,
    private val api: StocksApi,
    private val mapper: StockMapper
) : StockInfoInteractor {
    override fun insertFavoriteStock(stock: StockModel): Single<Unit> =
        Single
            .just(db.favoriteStocksDao().insertFavoriteStock(FavoriteStockEntity(0, stock.symbol!!)))


//    override fun getFavoritesStocks(stock: StockModel): Single<List<String>> =
//        Single
//            .just(db.favoriteStocksDao().getAll())
//            .map { stockEntityList ->
//
//            }
}