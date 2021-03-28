package com.example.mystocks.search_stocks

import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.model.StockModel

interface SearchStocksScreenContract {

    interface View {
        fun showStocksList(stocksList: List<StockModel>)
        fun showLargeProgress(show: Boolean)
        fun showSmallProgress(show: Boolean)
        fun showNotFoundStocksMessage(show: Boolean)
        fun showError(error: String)
    }

    interface Presenter {
        fun getDefaultStockList()
        fun getSearchedStock(symbol: String)
        fun changeFavorite(stock: StockModel)
        fun checkAvailableSearchedStocks(symbol: String)
        fun dispose()
    }
}