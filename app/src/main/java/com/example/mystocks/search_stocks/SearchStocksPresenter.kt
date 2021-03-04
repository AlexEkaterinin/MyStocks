package com.example.mystocks.search_stocks

import com.example.mystocks.api.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchStocksPresenter(
    private val view: SearchStocksContractView,
) {

    private val api = ServiceBuilder.api
    private val disposables: CompositeDisposable = CompositeDisposable()


    fun getDefaultStockList() {
        disposables.add(
            api.getDowJonesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showDefaultStocksList(it)
                    }, {
                        view.showError()
                    })
        )
    }

    fun dispose() {
        disposables.dispose()
    }
}



