package com.example.mystocks.favorite_stocks

import com.example.mystocks.model.StockModel

interface FavoriteStocksContractView {

    fun showFavoriteStocksList(favoriteList: List<StockModel>)

    fun showScreenOfAvailableStocks(isShow: Boolean)

    fun showError()
}