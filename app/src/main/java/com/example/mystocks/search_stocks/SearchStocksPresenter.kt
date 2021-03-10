package com.example.mystocks.search_stocks

import com.example.mystocks.api.ServiceBuilder
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchStocksPresenter(
    private val view: SearchStocksContractView,
    private val mapper: StockMapper
) {

    private val api = ServiceBuilder.api
    private val disposables: CompositeDisposable = CompositeDisposable()


    fun getDefaultStockList() {
        disposables.add(
            api.getDowJonesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { stockList ->

                        val stockInfoList = stockList.map(mapper::map)

                        view.showDefaultStocksList(stockInfoList)
                    }, {
                        view.showError()
                    })
        )
    }

    fun dispose() {
        disposables.dispose()
    }
}



