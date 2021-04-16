package com.example.mystocks.favorite_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.DiffCallback
import com.example.mystocks.R
import com.example.mystocks.model.StockModel

class FavoriteStocksAdapter(
    private val favoriteListener: (stock: StockModel) -> Unit,
    private val showCompanyProfileListener: (stock: StockModel) -> Unit
) : RecyclerView.Adapter<FavoriteStocksViewHolder>() {

    private var favoriteStocksList = mutableListOf<StockModel>()

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

    fun setData(newData: List<StockModel>) {
        val oldData = favoriteStocksList.toList()
        favoriteStocksList.clear()
        favoriteStocksList.addAll(newData)

        DiffUtil
            .calculateDiff(DiffCallback(favoriteStocksList, oldData), false)
            .dispatchUpdatesTo(this)
    }
}