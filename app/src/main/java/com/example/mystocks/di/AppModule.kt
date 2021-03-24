package com.example.mystocks.di

import androidx.fragment.app.Fragment
import com.example.mystocks.StockInfoInteractor
import com.example.mystocks.StockInfoInteractorImpl
import com.example.mystocks.favorite_stocks.FavoriteStocksFragment
import com.example.mystocks.favorite_stocks.FavoriteStocksPresenter
import com.example.mystocks.favorite_stocks.FavoriteStocksScreenContract
import com.example.mystocks.search_stocks.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@InstallIn(FragmentComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindSearchStocksFragment(impl: SearchStocksFragment): SearchStocksScreenContract.View

    @Binds
    abstract fun bindSearchStocksPresenter(impl: SearchStocksPresenter): SearchStocksScreenContract.Presenter

    @Binds
    abstract fun bindFavoriteStocksFragment(impl: FavoriteStocksFragment): FavoriteStocksScreenContract.View

    @Binds
    abstract fun bindFavoriteStocksPresenter(impl: FavoriteStocksPresenter): FavoriteStocksScreenContract.Presenter

    @Binds
    abstract fun bindSearchInfoInteractor(impl: StockInfoInteractorImpl): StockInfoInteractor
}

@InstallIn(FragmentComponent::class)
@Module
object SearchStocksFragmentModule {

    @Provides
    fun provideSearchStocksFragment(fragment: Fragment): SearchStocksFragment {
        return fragment as SearchStocksFragment
    }
}

@InstallIn(FragmentComponent::class)
@Module
object FavoriteStocksFragmentModule {

    @Provides
    fun provideFavoriteStocksFragment(fragment: Fragment): FavoriteStocksFragment {
        return fragment as FavoriteStocksFragment
    }
}
