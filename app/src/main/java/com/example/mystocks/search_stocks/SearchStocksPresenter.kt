package com.example.mystocks.search_stocks

import com.example.mystocks.SearchStocksInteractor
import com.example.mystocks.StockInfoRepository
import com.example.mystocks.model.StockModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchStocksPresenter @Inject constructor(
    private val view: SearchStocksContractView,
    private val interactor: SearchStocksInteractor,
    private val repository: StockInfoRepository
) : SearchStocksContractPresenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun getDefaultStockList() {
        disposables.add(
            interactor.getDefaultStocksList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { responseList ->
                        view.showStocksList(responseList)
                    }, {
                        view.showError()
                    })
        )
    }

    override fun getSearchedStock(symbol: String) {
        disposables.add(
            interactor.getSearchedStock(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseList ->
                    if(responseList.isEmpty()) {
                        view.showNotFoundStocksMessage(true)
                    } else {
                        view.showStocksList(responseList)
                    }
                }, {
                    view.showError()
                })
        )
    }

    override fun changeFavorite(stock: StockModel) {
        disposables.add(
            repository.changeFavorite(stock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun dispose() {
        disposables.dispose()
    }
}



