package com.example.mystocks.api

import com.google.gson.annotations.SerializedName

data class CompanyInfoResponseType(
    @SerializedName("address1")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("website")
    val website: String?,
    @SerializedName("industry")
    val industry: String?,
    @SerializedName("sector")
    val sector: String?,
    @SerializedName("longBusinessSummary")
    val longBusinessSummary: String?,
    @SerializedName("fullTimeEmployees")
    val fullTimeEmployees: Int?,
)