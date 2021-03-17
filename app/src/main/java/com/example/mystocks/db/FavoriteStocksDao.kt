package com.example.mystocks.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteStocksDao {
    @Query("SELECT favorite_symbol FROM favorite_stocks_table")
    fun getAllFavoriteStocks(): List<String>

    @Query("SELECT * FROM favorite_stocks_table WHERE favorite_symbol LIKE :symbol")
    fun getRequestedStocks(symbol: String): List<FavoriteStockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteStock(stock: FavoriteStockEntity)

    @Query("DELETE FROM favorite_stocks_table WHERE favorite_symbol LIKE :symbol")
    fun removeFavoriteStock(symbol: String)
}