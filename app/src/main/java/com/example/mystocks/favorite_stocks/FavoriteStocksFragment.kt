package com.example.mystocks.favorite_stocks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystocks.databinding.FavoriteStocksScreenBinding
import com.example.mystocks.isVisible
import com.example.mystocks.model.StockModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteStocksFragment : Fragment(), FavoriteStocksScreenContract.View {

    @Inject
    lateinit var presenter: FavoriteStocksScreenContract.Presenter

    private lateinit var binding: FavoriteStocksScreenBinding

    private val favoriteStocksAdapter: FavoriteStocksAdapter by lazy {
        FavoriteStocksAdapter(
            favoriteListener = { stock: StockModel, position: Int ->
                presenter.changeFavorite(stock)
                favoriteStocksAdapter.removeItem(position)
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
                favoriteStocksAdapter.filter(enteredText.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.checkAvailableFavoriteStocks()
    }

    override fun onDetach() {
        presenter.dispose()
        super.onDetach()
    }

    override fun showFavoriteStocksList(favoriteList: List<StockModel>) {
        favoriteStocksAdapter.setData(favoriteList)
    }

    override fun showScreenOfAvailableStocks(isShow: Boolean) {
        binding.searchField.isVisible(!isShow)
        binding.rvFavoriteStocks.isVisible(!isShow)
        binding.noFavoriteMessage.isVisible(isShow)
    }

    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}