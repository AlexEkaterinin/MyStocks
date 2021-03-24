package com.example.mystocks.mapper

import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.db.FavoriteStocksEntity
import com.example.mystocks.model.StockModel
import javax.inject.Inject

class StockMapper @Inject constructor() {

    fun fromResponseToModel(entity: StocksInfoResponseType) =
        StockModel(
            longName = entity.longName,
            symbol = entity.symbol,
            price = entity.price,
            currency = entity.currency,
            change = entity.change,
            changePercent = entity.changePercent,
            isFavorite = entity.isFavorite
        )

    fun fromModelToEntity(model: StockModel) =
        FavoriteStocksEntity(
            favoriteStockSymbol = model.symbol
        )
}