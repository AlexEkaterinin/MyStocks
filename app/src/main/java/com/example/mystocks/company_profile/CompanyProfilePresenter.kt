package com.example.mystocks.company_profile

import com.example.mystocks.StockInfoRepository
import com.example.mystocks.mapper.StockMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CompanyProfilePresenter @Inject constructor(
    private val view: CompanyProfileScreenContract.View,
    private val mapper: StockMapper,
    private val repository: StockInfoRepository
) : CompanyProfileScreenContract.Presenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun getCompanyProfileInfo(symbol: String?) {
        disposables.add(
            repository.getCompanyProfileInfo(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseCompanyInfo ->
                    view.showCompanyInfo(mapper.fromResponseToCompanyInfoModel(responseCompanyInfo))
                    view.showProgress(false)
                }, {
                    view.showError()
                })
        )
    }

    override fun inserFavoriteStock(symbol: String?) {
        disposables.add(
            repository.insertFavoriteStock(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun removeFavoriteStock(symbol: String?) {
        disposables.add(
            repository.removeFavoriteStock(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun dispose() {
        disposables.dispose()
    }
}