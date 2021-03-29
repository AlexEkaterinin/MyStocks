package com.example.mystocks.search_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.model.StockModel

class SearchStocksAdapter(
    private val favoriteListener: (stock: StockModel) -> Unit,
    private val showCompanyProfileListener: (stock: StockModel) -> Unit
) : RecyclerView.Adapter<SearchStocksViewHolder>() {

    private val listStocks: MutableList<StockModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchStocksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return SearchStocksViewHolder(
            view,
            favoriteListener,
            showCompanyProfileListener
        )
    }

    override fun onBindViewHolder(holder: SearchStocksViewHolder, position: Int) {
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

    fun clearData() {
        listStocks.clear()
        notifyDataSetChanged()
    }
}