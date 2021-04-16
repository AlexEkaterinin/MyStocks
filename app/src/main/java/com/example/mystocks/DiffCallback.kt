package com.example.mystocks

import androidx.recyclerview.widget.DiffUtil
import com.example.mystocks.model.StockModel

class DiffCallback(
    private val newData: List<StockModel>,
    private val oldData: List<StockModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldData[oldItemPosition] == newData[newItemPosition]
}