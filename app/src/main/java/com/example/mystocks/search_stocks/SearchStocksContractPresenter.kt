package com.example.mystocks.search_stocks

import com.example.mystocks.model.StockModel

interface SearchStocksContractPresenter {

    fun getDefaultStockList()

    fun getSearchedStock(symbol: String)

    fun changeFavorite(stock: StockModel)

    fun checkAvailableSearchedStocks(symbol: String)

    fun dispose()
}