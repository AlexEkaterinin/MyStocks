package com.example.mystocks.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StocksApi {
    @GET(
        "qu/quote/?" +
                "apikey=z5YZZXUlufcRP5FLXDCVCB0sJ2RbYumfJ0bXN7ol2l5MNrtC1ZvhQkdsiozr&symbol=" +
                "UNH,GS,HD,MSFT,AMGN,BA,CAT,V,CRM,MCD,HON,DIS,MMM,JNJ,JPM,TRV,AXP,NKE,WMT,AAPL,PG,IBM,CVX,MRK,DOW,INTC,VZ,KO,WBA,CSCO"
    )
    fun getDowJonesList(): Single<List<StocksInfoResponseType>>

    @GET(
        "qu/quote/?" +
                "apikey=z5YZZXUlufcRP5FLXDCVCB0sJ2RbYumfJ0bXN7ol2l5MNrtC1ZvhQkdsiozr&"
    )
    fun getSpecificStocksList(
        @Query("symbol") specificStocks: String?
    ): Single<List<StocksInfoResponseType>>

    @GET(
        "qu/quote/profile/?" +
                "apikey=z5YZZXUlufcRP5FLXDCVCB0sJ2RbYumfJ0bXN7ol2l5MNrtC1ZvhQkdsiozr&"
    )
    fun getCompanyProfileInfo(
        @Query("symbol") companySymbol: String?
    ): Single<CompanyInfoResponseType>
}