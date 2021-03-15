package com.example.mystocks.search_stocks

import com.example.mystocks.api.StocksApi
import com.example.mystocks.mapper.StockMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchStocksPresenter @Inject constructor(
    private val mapper: StockMapper,
    private val api: StocksApi,
    private val view: SearchStocksScreenContract.View
): SearchStocksScreenContract.Presenter {

    private val disposables: CompositeDisposable = CompositeDisposable()


    override fun getDefaultStockList() {
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

//    fun insertFavoriteStock(stock: StockModel) {
//        disposables.add(
//            interactor.insertFavoriteStock(stock)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()
//        )
//    }


    override fun dispose() {
        disposables.dispose()
    }
}



