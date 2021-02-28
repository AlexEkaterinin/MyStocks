package com.example.mystocks.search_stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystocks.R

class SearchStocksFragment : Fragment() {

    private lateinit var rvNum: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_stocks_screen, container, false)

        rvNum = root.findViewById(R.id.rv_stocks)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNum.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = Adapter()
        }
    }
}