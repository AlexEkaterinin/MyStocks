package com.example.mystocks.mapper

import com.example.mystocks.api.CompanyInfoResponseType
import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.db.FavoriteStocksEntity
import com.example.mystocks.model.CompanyProfileModel
import com.example.mystocks.model.StockModel
import javax.inject.Inject

class StockMapper @Inject constructor() {

    fun fromResponseToStockModel(entity: StocksInfoResponseType) =
        StockModel(
            longName = entity.longName,
            symbol = entity.symbol,
            price = entity.price,
            currency = entity.currency,
            change = entity.change,
            changePercent = entity.changePercent,
            isFavorite = entity.isFavorite
        )

    fun fromModelToStockEntity(symbol: String?) =
        FavoriteStocksEntity(
            favoriteStockSymbol = symbol
        )

    fun fromResponseToCompanyInfoModel(entity: CompanyInfoResponseType) =
        CompanyProfileModel(
            address = entity.address,
            city = entity.city,
            state = entity.state,
            country = entity.country,
            website = entity.website,
            industry = entity.industry,
            sector = entity.sector,
            longBusinessSummary = entity.longBusinessSummary,
            fullTimeEmployees = entity.fullTimeEmployees
        )
}