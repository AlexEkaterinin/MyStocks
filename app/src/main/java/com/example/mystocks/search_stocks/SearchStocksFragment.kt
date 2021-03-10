package com.example.mystocks.search_stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystocks.databinding.SearchStocksScreenBinding
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel

class SearchStocksFragment : Fragment(), SearchStocksContractView {

    private lateinit var binding: SearchStocksScreenBinding
    private val stocksAdapter: SearchStocksAdapter by lazy {
        SearchStocksAdapter(
            favoriteListener = { stock: StockModel ->
                stock.isFavorite = !stock.isFavorite
                stocksAdapter.notifyDataSetChanged()
            }
        )
    }

    private val presenter: SearchStocksPresenter by lazy {
        SearchStocksPresenter(this, StockMapper())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchStocksScreenBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvStocks = binding.rvStocks

        rvStocks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stocksAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getDefaultStockList()
    }

    override fun onDestroyView() {
        presenter.dispose()
        super.onDestroyView()
    }

    override fun showDefaultStocksList(defaultList: List<StockModel>) {
        stocksAdapter.setData(defaultList)
    }

    override fun showError() {
        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
    }
}