package com.example.mystocks.model

data class CompanyProfileModel(
    val address: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val website: String?,
    val industry: String?,
    val sector: String?,
    val longBusinessSummary: String?,
    val fullTimeEmployees: Int?,
)
