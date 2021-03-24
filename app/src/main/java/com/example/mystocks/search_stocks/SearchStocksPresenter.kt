package com.example.mystocks.search_stocks

import com.example.mystocks.StockInfoInteractor
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchStocksPresenter @Inject constructor(
    private val mapper: StockMapper,
    private val view: SearchStocksScreenContract.View,
    private val interactor: StockInfoInteractor
) : SearchStocksScreenContract.Presenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun getDefaultStockList() {
        disposables.add(
            interactor.getDefaultStocksList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { responseList ->
                        val stockInfoList = responseList.map(mapper::fromResponseToModel)
                        view.showDefaultStocksList(stockInfoList)
                    }, {
                        view.showError()
                    })
        )
    }

    override fun changeFavorite(stock: StockModel) {
        disposables.add(
            interactor.changeFavorite(stock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun dispose() {
        disposables.dispose()
    }
}



