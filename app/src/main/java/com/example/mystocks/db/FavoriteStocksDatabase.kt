package com.example.mystocks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteStocksEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FavoriteStocksDatabase : RoomDatabase() {

    abstract fun favoriteStocksDao(): FavoriteStocksDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteStocksDatabase? = null

        fun getInstance(context: Context): FavoriteStocksDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteStocksDatabase::class.java,
                        "favorite_stocks_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}