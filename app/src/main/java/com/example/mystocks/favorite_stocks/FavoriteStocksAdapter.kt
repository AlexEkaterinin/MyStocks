package com.example.mystocks.favorite_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.model.StockModel
import java.util.*

class FavoriteStocksAdapter(
    private val favoriteListener: (stock: StockModel, position: Int) -> Unit,
    private val showCompanyProfileListener: (stock: StockModel) -> Unit
) : RecyclerView.Adapter<FavoriteStocksViewHolder>() {

    private var favoriteStocksList = mutableListOf<StockModel>()

    private val firstFavoriteStockList = mutableListOf<StockModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteStocksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return FavoriteStocksViewHolder(
            view,
            favoriteListener,
            showCompanyProfileListener
        )
    }

    override fun onBindViewHolder(holder: FavoriteStocksViewHolder, position: Int) {
        holder.bind(favoriteStocksList[position])
    }

    override fun getItemCount(): Int {
        return favoriteStocksList.size
    }

    fun isStockLast(): Boolean {
        return favoriteStocksList.size == 0
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

        favoriteStocksList = if(symbols.isNotEmpty()) {
            for (stock in firstFavoriteStockList) {
                if(stock.symbol.toLowerCase(Locale.ROOT).contains(symbols)) filteredFavoriteStocksList.add(stock)
            }
            filteredFavoriteStocksList

        } else {
            firstFavoriteStockList
        }
        notifyDataSetChanged()
    }

    fun removeItem(stock: StockModel, position: Int) {
        favoriteStocksList.remove(stock)
        firstFavoriteStockList.remove(stock)
        notifyItemRemoved(position)
    }
}