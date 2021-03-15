package com.example.mystocks.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_stocks")
data class FavoriteStockEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "favorite_symbol")
    val favoriteStockSymbol: String
)
