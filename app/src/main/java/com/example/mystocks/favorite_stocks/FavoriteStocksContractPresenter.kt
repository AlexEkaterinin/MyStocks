package com.example.mystocks.favorite_stocks

import com.example.mystocks.model.StockModel

interface FavoriteStocksContractPresenter {

    fun getFavoriteStocksList()

    fun changeFavorite(stock: StockModel)

    fun checkAvailableFavoriteStocks()

    fun filterStocksList(symbols: String)

    fun removeFavoriteStock(stock: StockModel)

    fun dispose()
}