package com.example.mystocks.search_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.api.StockInfoResponseType
import com.example.mystocks.model.StockModel

class SearchStocksAdapter(
    private val favoriteListener: (stock: StockModel) -> Unit
) : RecyclerView.Adapter<NumbersViewHolder>() {

    private val listStocks: MutableList<StockModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return NumbersViewHolder(
            view,
            favoriteListener
        )
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.bind(listStocks[position])
    }

    override fun getItemCount(): Int {
        return listStocks.size
    }

    fun setData(list: List<StockModel>) {
        listStocks.clear()
        listStocks.addAll(list)
        notifyDataSetChanged()
    }
}