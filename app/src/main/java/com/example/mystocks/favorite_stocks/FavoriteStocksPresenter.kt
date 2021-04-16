package com.example.mystocks.favorite_stocks

import com.example.mystocks.StockInfoRepository
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteStocksPresenter @Inject constructor(
    private val view: FavoriteStocksContractView,
    private val interactor: FavoriteStocksInteractor,
    private val repository: StockInfoRepository
) : FavoriteStocksContractPresenter {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun getFavoriteStocksList() {
        disposables.add(
            interactor.getFavoriteStocksList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseList ->
                    view.showFavoriteStocksList(responseList)
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

    override fun filterStocksList(symbols: String) {
        disposables.add(
            interactor.filterStocksList(symbols)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { filteredList ->
                    view.showFavoriteStocksList(filteredList)
                }
        )
    }

    override fun removeFavoriteStock(stock: StockModel) {
        disposables.add(
            interactor.removeFavoriteStock(stock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { favoriteStockList ->
                    if(favoriteStockList.isEmpty()) view.showScreenOfAvailableStocks(true)
                    view.showFavoriteStocksList(favoriteStockList)
                }
        )
    }

    override fun dispose() {
        disposables.dispose()
    }
}