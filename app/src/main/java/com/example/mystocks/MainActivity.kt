package com.example.mystocks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mystocks.databinding.MainActivityBinding
import com.example.mystocks.favorite_stocks.FavoriteStocksFragment
import com.example.mystocks.search_stocks.SearchStocksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private val searchStocksFragment = SearchStocksFragment()
    private val favoriteStocksFragment = FavoriteStocksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentsContainer, searchStocksFragment)
            .commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)
        binding.bottomNavigation.selectedItemId = R.id.search_stocks
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.search_stocks -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentsContainer, searchStocksFragment)
                    .addToBackStack(searchStocksFragment::class.java.canonicalName)
                    .commit()
                true
            }
            R.id.favorite_stocks -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentsContainer, favoriteStocksFragment)
                    .addToBackStack(favoriteStocksFragment::class.java.canonicalName)
                    .commit()
                true
            }
            else -> false
        }
    }

}