package com.example.mystocks.favorite_stocks

import com.example.mystocks.StockInfoRepository
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

interface FavoriteStocksInteractor {

    fun getFavoriteStocksList(): Single<List<StockModel>>

    fun checkAvailableFavoriteStocks(): Single<Boolean>

    fun filterStocksList(symbols: String): Single<List<StockModel>>

    fun removeFavoriteStock(stock: StockModel): Single<List<StockModel>>
}

class FavoriteStocksInteractorImpl @Inject constructor(
    private val repository: StockInfoRepository,
    private val mapper: StockMapper
) : FavoriteStocksInteractor {

    private val favoriteStocksList = mutableListOf<StockModel>()

    override fun checkAvailableFavoriteStocks(): Single<Boolean> =
        repository.getAllFavoriteLocalStocks()
            .map { favoriteList ->
                favoriteList.isEmpty()
            }

    override fun filterStocksList(symbols: String): Single<List<StockModel>> =
        Single.fromCallable {
            var filteredFavoriteStocksList = mutableListOf<StockModel>()
            filteredFavoriteStocksList = if (symbols.isNotEmpty()) {
                for (stock in favoriteStocksList) {
                    if (stock.symbol.toLowerCase(Locale.ENGLISH)
                            .contains(symbols)
                    ) filteredFavoriteStocksList.add(stock)
                }
                filteredFavoriteStocksList

            } else {
                favoriteStocksList
            }
            filteredFavoriteStocksList
        }

    override fun removeFavoriteStock(stock: StockModel): Single<List<StockModel>> =
        Single.fromCallable {
            favoriteStocksList.remove(stock)
            favoriteStocksList
        }

    override fun getFavoriteStocksList(): Single<List<StockModel>> =
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
                favoriteStocksList.clear()
                favoriteStocksList.addAll(responseList.map(mapper::fromResponseToStockModel))
                favoriteStocksList
            }
}