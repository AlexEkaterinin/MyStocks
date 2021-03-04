package com.example.mystocks.search_stocks

import com.example.mystocks.api.StockInfoResponseType

interface SearchStocksContractView {
    fun showDefaultStocksList(defaultList: List<StockInfoResponseType>)
    fun showError()
}