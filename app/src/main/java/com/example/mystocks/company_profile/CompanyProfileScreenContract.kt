package com.example.mystocks.company_profile

import com.example.mystocks.model.CompanyProfileModel

interface CompanyProfileScreenContract {

    interface View {
        fun showCompanyInfo(companyInfo: CompanyProfileModel)
        fun showProgress(show: Boolean)
        fun showError()
    }

    interface Presenter {
        fun getCompanyProfileInfo(symbol: String?)
        fun inserFavoriteStock(symbol: String?)
        fun removeFavoriteStock(symbol: String?)
        fun dispose()
    }
}