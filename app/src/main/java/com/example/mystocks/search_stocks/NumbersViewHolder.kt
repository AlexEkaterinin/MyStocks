package com.example.mystocks.search_stocks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.Number
import com.example.mystocks.R

class NumbersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var number: TextView = itemView.findViewById(R.id.number)

    fun bind(item: Number) {

        number.text = item.number.toString()
    }
}