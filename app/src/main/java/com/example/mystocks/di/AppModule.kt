package com.example.mystocks.di

import androidx.fragment.app.Fragment
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
}

@InstallIn(FragmentComponent::class)
@Module
object SearchStocksFragmentModule {

    @Provides
    fun provideSearchStocksFragment(fragment: Fragment): SearchStocksFragment {
        return fragment as SearchStocksFragment
    }
}