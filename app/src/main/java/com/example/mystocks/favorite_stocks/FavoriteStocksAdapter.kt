package com.example.mystocks.favorite_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.model.StockModel

class FavoriteStocksAdapter(
    private val favoriteListener: (stock: StockModel) -> Unit
) : RecyclerView.Adapter<FavoriteStocksViewHolder>() {

    private val favoriteStocksList: MutableList<StockModel> = mutableListOf()

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
        notifyDataSetChanged()
    }
}