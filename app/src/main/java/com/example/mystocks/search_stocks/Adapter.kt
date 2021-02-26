package com.example.mystocks.search_stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.Number
import com.example.mystocks.R

class Adapter : RecyclerView.Adapter<NumbersViewHolder>() {

    private var numbersList = mutableListOf<Number>(
        Number(1),
        Number(2),
        Number(3),
        Number(4),
        Number(5),
        Number(6),
        Number(7),
        Number(8),
        Number(9),
        Number(10)
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return NumbersViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.bind(numbersList[position])
    }

    override fun getItemCount(): Int {
        return numbersList.size
    }
}