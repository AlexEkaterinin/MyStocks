package com.example.mystocks.favorite_stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mystocks.R

class FavoriteStocksFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
         inflater.inflate(R.layout.favorite_stocks_screen, container, false)

}