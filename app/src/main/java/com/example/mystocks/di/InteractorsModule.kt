package com.example.mystocks.di

import com.example.mystocks.StockInfoInteractor
import com.example.mystocks.StockInfoInteractorImpl
import com.example.mystocks.StockInfoRepository
import com.example.mystocks.StocksInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class InteractorsModule {

    @Binds
    abstract fun bindStocksInfoInteractor(impl: StockInfoInteractorImpl): StockInfoInteractor

    @Binds
    abstract fun bindStocksInfoRepository(impl: StocksInfoRepositoryImpl): StockInfoRepository
}