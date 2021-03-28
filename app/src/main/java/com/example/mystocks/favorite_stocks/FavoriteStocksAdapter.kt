package com.example.mystocks.favorite_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.model.StockModel
import java.util.*

class FavoriteStocksAdapter(
    private val favoriteListener: (stock: StockModel, position: Int) -> Unit
) : RecyclerView.Adapter<FavoriteStocksViewHolder>() {

    private var favoriteStocksList = mutableListOf<StockModel>()

    private val firstFavoriteStockList = mutableListOf<StockModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStocksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return FavoriteStocksViewHolder(
            view,
            favoriteListener
        )
    }

    override fun onBindViewHolder(holder: FavoriteStocksViewHolder, position: Int) {
        holder.bind(favoriteStocksList[position])
    }

    override fun getItemCount(): Int {
        return favoriteStocksList.size
    }

    fun setData(list: List<StockModel>) {
        favoriteStocksList.clear()
        favoriteStocksList.addAll(list)
        firstFavoriteStockList.clear()
        firstFavoriteStockList.addAll(list)
        notifyDataSetChanged()
    }

    fun filter(symbols: String) {
        val filteredFavoriteStocksList = mutableListOf<StockModel>()
        if(symbols.isNotEmpty()) {
            for (stock in firstFavoriteStockList) {
                if(stock.symbol.toLowerCase(Locale.ROOT).contains(symbols)) filteredFavoriteStocksList.add(stock)
            }
            favoriteStocksList = filteredFavoriteStocksList
            notifyDataSetChanged()
        } else {
            favoriteStocksList = firstFavoriteStockList
            notifyDataSetChanged()
        }
    }

    fun removeItem(position: Int) {
        favoriteStocksList.removeAt(position)
        firstFavoriteStockList.removeAt(position)
        notifyItemRemoved(position)
    }
}