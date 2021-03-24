package com.example.mystocks.api

import com.google.gson.annotations.SerializedName

data class StocksInfoResponseType(
    @SerializedName("longName")
    val longName: String?,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("ask")
    val price: Float?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("regularMarketChange")
    val change: Float?,
    @SerializedName("regularMarketChangePercent")
    val changePercent: Float?,
    var isFavorite: Boolean = false
)
