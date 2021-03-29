package com.example.mystocks.search_stocks

import com.example.mystocks.model.StockModel

interface SearchStocksScreenContract {

    interface View {
        fun showStocksList(stocksList: List<StockModel>)
        fun showNotFoundStocksMessage(show: Boolean)
        fun showError()
    }

    interface Presenter {
        fun getDefaultStockList()
        fun getSearchedStock(symbol: String)
        fun changeFavorite(stock: StockModel)
        fun checkAvailableSearchedStocks(symbol: String)
        fun dispose()
    }
}