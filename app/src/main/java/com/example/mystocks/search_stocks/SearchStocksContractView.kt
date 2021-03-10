package com.example.mystocks.search_stocks

import com.example.mystocks.model.StockModel

interface SearchStocksContractView {
    fun showDefaultStocksList(defaultList: List<StockModel>)
    fun showError()
}