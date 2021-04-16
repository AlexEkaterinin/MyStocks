package com.example.mystocks.search_stocks

import com.example.mystocks.model.StockModel

interface SearchStocksContractView {

    fun showStocksList(stocksList: List<StockModel>)

    fun showNotFoundStocksMessage(show: Boolean)

    fun showError()
}