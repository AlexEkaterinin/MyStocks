package com.example.mystocks.mapper

import com.example.mystocks.api.StockInfoResponseType
import com.example.mystocks.model.StockModel

class StockMapper {
    fun map(entity: StockInfoResponseType) =
        StockModel(
            longName = entity.longName,
            symbol = entity.symbol,
            price = entity.price,
            currency = entity.currency,
            change = entity.change,
            changePercent = entity.changePercent,
            isFavorite = false
        )
}