package com.example.mystocks.di

import androidx.fragment.app.Fragment
import com.example.mystocks.SearchStocksInteractor
import com.example.mystocks.SearchStocksInteractorImpl
import com.example.mystocks.favorite_stocks.*
import com.example.mystocks.search_stocks.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@InstallIn(FragmentComponent::class)
@Module
abstract class MainScreenModule {

    @Binds
    abstract fun bindSearchStocksFragment(impl: SearchStocksFragment): SearchStocksContractView

    @Binds
    abstract fun bindSearchStocksPresenter(impl: SearchStocksPresenter): SearchStocksContractPresenter

    @Binds
    abstract fun bindSearchStocksInteractor(impl: SearchStocksInteractorImpl): SearchStocksInteractor

    @Binds
    abstract fun bindFavoriteStocksFragment(impl: FavoriteStocksFragment): FavoriteStocksContractView

    @Binds
    abstract fun bindFavoriteStocksPresenter(impl: FavoriteStocksPresenter): FavoriteStocksContractPresenter

    @Binds
    abstract fun bindFavoriteStocksInteractor(impl: FavoriteStocksInteractorImpl): FavoriteStocksInteractor
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