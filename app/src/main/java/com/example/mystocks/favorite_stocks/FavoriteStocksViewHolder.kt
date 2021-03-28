package com.example.mystocks.favorite_stocks

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R
import com.example.mystocks.model.StockModel

class FavoriteStocksViewHolder(
    itemView: View,
    private val favoriteListener: (stock: StockModel, position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private var shortName: TextView = itemView.findViewById(R.id.short_name)
    private var longName: TextView = itemView.findViewById(R.id.long_name)
    private var price: TextView = itemView.findViewById(R.id.price)
    private var change: TextView = itemView.findViewById(R.id.change)
    private var favoriteBtn: ImageButton = itemView.findViewById(R.id.favorite_btn)
    private var increaseColor = 0
    private var decreaseColor = 0

    init {
        increaseColor = ContextCompat.getColor(itemView.context, R.color.increasePriceColor)
        decreaseColor = ContextCompat.getColor(itemView.context, R.color.decreasePriceColor)
    }


    fun bind(item: StockModel) {

        shortName.text = item.symbol
        longName.text = item.longName
        price.text = item.price.toString() + " " + item.currency
        favoriteBtn.isSelected = item.isFavorite

        with(change) {
            text = "%.2f".format(item.change) + " (" + "%.2f".format(item.changePercent) + "%)"

            if (item.change != null && item.change > 0) {
                setTextColor(increaseColor)
            } else if (item.change != null && item.change < 0) {
                setTextColor(decreaseColor)
            }
        }

        favoriteBtn.setOnClickListener {
            favoriteListener.invoke(item, adapterPosition)
        }
    }
}