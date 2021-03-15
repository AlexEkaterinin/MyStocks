package com.example.mystocks.search_stocks

import com.example.mystocks.model.StockModel

interface SearchStocksScreenContract {

    interface View {
        fun showDefaultStocksList(defaultList: List<StockModel>)
        fun showError()
    }

    interface Presenter {
        fun getDefaultStockList()
        fun dispose()
    }
}