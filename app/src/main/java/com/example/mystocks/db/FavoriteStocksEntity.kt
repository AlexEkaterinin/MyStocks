package com.example.mystocks.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_stocks_table")
data class FavoriteStocksEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "favorite_symbol")
    val favoriteStockSymbol: String?
)
