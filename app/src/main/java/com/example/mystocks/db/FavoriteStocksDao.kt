package com.example.mystocks.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteStocksDao {
    @Query("SELECT * FROM favorite_stocks")
    fun getAll(): List<FavoriteStockEntity>

    @Query("SELECT * FROM favorite_stocks WHERE favorite_symbol LIKE :symbol")
    fun getRequestedStocks(symbol: String): List<FavoriteStockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteStock(stock: FavoriteStockEntity)
}