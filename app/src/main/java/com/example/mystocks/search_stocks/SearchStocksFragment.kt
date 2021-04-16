package com.example.mystocks.search_stocks

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystocks.company_profile.CompanyProfileActivity
import com.example.mystocks.databinding.SearchStocksScreenBinding
import com.example.mystocks.isVisible
import com.example.mystocks.model.StockModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchStocksFragment : Fragment(), SearchStocksContractView {

    @Inject
    lateinit var presenter: SearchStocksContractPresenter

    private lateinit var binding: SearchStocksScreenBinding

    private val searchStocksAdapter: SearchStocksAdapter by lazy {
        SearchStocksAdapter(
            favoriteListener = { stock: StockModel ->
                presenter.changeFavorite(stock)
                stock.isFavorite = !stock.isFavorite
                searchStocksAdapter.notifyDataSetChanged()
            },
            showCompanyProfileListener = { stock: StockModel ->
                showCompanyProfile(stock)
            }
        )
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
            adapter = searchStocksAdapter
        }

        binding.searchField.addTextChangedListener(object : TextWatcher {
            var timer = Timer()

            override fun beforeTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer.cancel()
                timer = Timer()
            }

            override fun afterTextChanged(enteredText: Editable?) {
                showSmallProgress(true)

                if (enteredText?.isEmpty() == true) {
                    presenter.getDefaultStockList()
                } else {
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                        presenter.checkAvailableSearchedStocks(enteredText.toString())
                        }
                    }, 1000)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        showLargeProgress(true)
        showSmallProgress(false)
        if(binding.searchField.text.isEmpty()) presenter.getDefaultStockList()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showStocksList(stocksList: List<StockModel>) {
        searchStocksAdapter.setData(stocksList)
        STOCKS_LIST_HAS_SET = true
        showSmallProgress(false)
        showLargeProgress(true)
        showNotFoundStocksMessage(false)
    }

    override fun showNotFoundStocksMessage(show: Boolean) {
        if(show) {
            showSmallProgress(!show)
            searchStocksAdapter.clearData()
            binding.notFoundMessage.isVisible(show)
        } else binding.notFoundMessage.isVisible(show)
    }

    override fun showError() {
        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
    }

    private fun showCompanyProfile(stock: StockModel) {
        val intent = Intent(activity,CompanyProfileActivity::class.java)
        intent.putExtra("symbol", stock.symbol)
        intent.putExtra("companyName", stock.longName)
        intent.putExtra("isFavorite", stock.isFavorite)
        startActivity(intent)
    }

    private fun showLargeProgress(show: Boolean) {
        if (STOCKS_LIST_HAS_SET) {
            binding.largeProgressBar.isVisible(!show)
        } else binding.largeProgressBar.isVisible(show)
    }

    private fun showSmallProgress(show: Boolean) {
        binding.smallProgressBar.isVisible(show)
    }

    companion object {
        private var STOCKS_LIST_HAS_SET = false
    }
}