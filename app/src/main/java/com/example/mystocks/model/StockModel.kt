package com.example.mystocks.model

data class StockModel(
    val longName: String?,
    val symbol: String,
    val price: Float?,
    val currency: String?,
    val change: Float?,
    val changePercent: Float?,
    var isFavorite: Boolean
)

