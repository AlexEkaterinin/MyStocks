package com.example.mystocks.search_stocks

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.api.StockInfoResponseType

class NumbersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var shortName: TextView = itemView.findViewById(R.id.short_name)
    private var longName: TextView = itemView.findViewById(R.id.long_name)
    private var price: TextView = itemView.findViewById(R.id.price)
    private var change: TextView = itemView.findViewById(R.id.change)
    private var favoriteBtn: ImageView = itemView.findViewById(R.id.favorite_btn)


    fun bind(item: StockInfoResponseType) {

        shortName.text = item.symbol
        longName.text = item.longName
        price.text = item.price.toString() + " " + item.currency
        favoriteBtn.setSelected(false)


        with(change) {
            text = "%.2f".format(item.change) + " (" + "%.2f".format(item.changePercent) + "%)"

            if (item.change != null && item.change > 0) {
                setTextColor(ContextCompat.getColor(context, R.color.IncreasePriceColor))
            } else if (item.change != null && item.change < 0) {
                setTextColor(ContextCompat.getColor(context, R.color.DecreasePriceColor))
            }
        }

        favoriteBtn.setOnClickListener {
            favoriteBtn.setSelected(!favoriteBtn.isSelected)
        }
    }
}