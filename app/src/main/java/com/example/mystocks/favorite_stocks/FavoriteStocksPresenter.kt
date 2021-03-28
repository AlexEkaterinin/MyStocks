package com.example.mystocks.favorite_stocks

import com.example.mystocks.StockInfoInteractor
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteStocksPresenter @Inject constructor(
    private val mapper: StockMapper,
    private val view: FavoriteStocksScreenContract.View,
    private val interactor: StockInfoInteractor
) : FavoriteStocksScreenContract.Presenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun getFavoriteStocksList() {
        disposables.add(
            interactor.getFavoriteStocksList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseList ->
                    val stockInfoList = responseList.map(mapper::fromResponseToModel)
                    view.showFavoriteStocksList(stockInfoList)
                }, {
                    view.showError(it.printStackTrace().toString())
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

    override fun checkAvailableFavoriteStocks() {
        disposables.add(
            interactor.checkAvailableFavoriteStocks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { favoriteListIsEmpty ->
                    view.showScreenOfAvailableStocks(favoriteListIsEmpty)
                    if (!favoriteListIsEmpty) getFavoriteStocksList()
                }
        )
    }

    override fun dispose() {
        disposables.dispose()
    }
}