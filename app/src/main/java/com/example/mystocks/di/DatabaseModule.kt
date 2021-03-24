package com.example.mystocks.di

import android.content.Context
import androidx.room.Room
import com.example.mystocks.db.FavoriteStocksDao
import com.example.mystocks.db.FavoriteStocksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteStocksDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteStocksDatabase::class.java,
            "favorite_stocks_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(database: FavoriteStocksDatabase): FavoriteStocksDao {
        return database.favoriteStocksDao()
    }
}