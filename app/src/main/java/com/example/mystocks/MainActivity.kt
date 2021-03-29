package com.example.mystocks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                val currentFragment = when (CURRENT_SCREEN_ID) {
                    R.id.search_stocks -> SearchStocksFragment()
                    R.id.favorite_stocks -> FavoriteStocksFragment()
                    else -> searchStocksFragment
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentsContainer, currentFragment)
                    .commit()

                binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)
                binding.bottomNavigation.selectedItemId = CURRENT_SCREEN_ID

            } else {
                supportFragmentManager.beginTransaction()
                    .remove(searchStocksFragment)
                    .remove(favoriteStocksFragment)
                    .commit()
            }

            showDisconnectedMessage(isConnected)
        })
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.search_stocks -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentsContainer, searchStocksFragment)
                    .addToBackStack(searchStocksFragment::class.java.canonicalName)
                    .commit()
                CURRENT_SCREEN_ID = R.id.search_stocks
                true
            }
            R.id.favorite_stocks -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentsContainer, favoriteStocksFragment)
                    .addToBackStack(favoriteStocksFragment::class.java.canonicalName)
                    .commit()
                CURRENT_SCREEN_ID = R.id.favorite_stocks
                true
            }
            else -> false
        }
    }

    private fun showDisconnectedMessage(show: Boolean) {
        binding.layoutDisconnected.isVisible(!show)
        binding.bottomNavigation.isVisible(show)
    }

    companion object {
        private var CURRENT_SCREEN_ID = R.id.search_stocks
    }
}