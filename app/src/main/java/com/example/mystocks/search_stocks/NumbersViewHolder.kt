package com.example.mystocks.search_stocks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.api.StockInfoResponseType

class NumbersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var number: TextView = itemView.findViewById(R.id.number)

    fun bind(item: StockInfoResponseType) {

        number.text = item.toString()
    }
}