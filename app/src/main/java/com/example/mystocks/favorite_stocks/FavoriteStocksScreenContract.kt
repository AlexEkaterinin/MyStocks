package com.example.mystocks.favorite_stocks

import com.example.mystocks.model.StockModel

interface FavoriteStocksScreenContract {

    interface View {
        fun showFavoriteStocksList(favoriteList: List<StockModel>)
        fun showScreenOfAvailableStocks(isShow: Boolean)
        fun showError(error: String)
    }

    interface Presenter {
        fun getFavoriteStocksList()
        fun changeFavorite(stock: StockModel)
        fun checkAvailableFavoriteStocks()
        fun dispose()
    }

}