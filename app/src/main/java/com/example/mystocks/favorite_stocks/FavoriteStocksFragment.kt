package com.example.mystocks.favorite_stocks

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
import com.example.mystocks.databinding.FavoriteStocksScreenBinding
import com.example.mystocks.isVisible
import com.example.mystocks.model.StockModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteStocksFragment : Fragment(), FavoriteStocksContractView {

    @Inject
    lateinit var presenter: FavoriteStocksContractPresenter

    private lateinit var binding: FavoriteStocksScreenBinding

    private val favoriteStocksAdapter: FavoriteStocksAdapter by lazy {
        FavoriteStocksAdapter(
            favoriteListener = { stock: StockModel ->
                presenter.changeFavorite(stock)
                presenter.removeFavoriteStock(stock)
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
        binding = FavoriteStocksScreenBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvFavoriteStocks = binding.rvFavoriteStocks

        rvFavoriteStocks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteStocksAdapter
        }

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(c: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(enteredText: Editable?) {
                presenter.filterStocksList(enteredText.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.checkAvailableFavoriteStocks()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    override fun showFavoriteStocksList(favoriteList: List<StockModel>) {
        favoriteStocksAdapter.setData(favoriteList)
    }

    override fun showScreenOfAvailableStocks(isShow: Boolean) {
        binding.searchField.isVisible(!isShow)
        binding.rvFavoriteStocks.isVisible(!isShow)
        binding.noFavoriteMessage.isVisible(isShow)
    }

    override fun showError() {
        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
    }

    private fun showCompanyProfile(stock: StockModel) {
        val intent = Intent(activity, CompanyProfileActivity::class.java)
        intent.putExtra("symbol", stock.symbol)
        intent.putExtra("companyName", stock.longName)
        intent.putExtra("isFavorite", stock.isFavorite)
        startActivity(intent)
    }
}