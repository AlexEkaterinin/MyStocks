package com.example.mystocks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
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

        showNavigation()

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                val currentFragment = when (CURRENT_SCREEN_ID) {
                    R.id.search_stocks -> supportFragmentManager.findFragmentById(R.id.search_stocks)
                    R.id.favorite_stocks -> supportFragmentManager.findFragmentById(R.id.favorite_stocks)
                    else -> supportFragmentManager.findFragmentById(R.id.search_stocks)
                }

                val oldFragment = when (OLD_SCREEN_ID) {
                    R.id.search_stocks -> supportFragmentManager.findFragmentById(R.id.search_stocks)
                    R.id.favorite_stocks -> supportFragmentManager.findFragmentById(R.id.favorite_stocks)
                    else -> supportFragmentManager.findFragmentById(R.id.search_stocks)
                }

                if (currentFragment != null && oldFragment != null) {
                    supportFragmentManager.beginTransaction()
                        .show(currentFragment)
                        .hide(oldFragment)
                        .setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
                        .setMaxLifecycle(oldFragment, Lifecycle.State.STARTED)
                        .commit()
                }

                binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)
                binding.bottomNavigation.selectedItemId = CURRENT_SCREEN_ID

            } else {
                supportFragmentManager.beginTransaction()
                    .hide(searchStocksFragment)
                    .hide(favoriteStocksFragment)
                    .commit()
            }

            showDisconnectedMessage(isConnected)
        })
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.search_stocks -> {
                supportFragmentManager.beginTransaction()
                    .show(searchStocksFragment)
                    .hide(favoriteStocksFragment)
                    .setMaxLifecycle(searchStocksFragment, Lifecycle.State.RESUMED)
                    .setMaxLifecycle(favoriteStocksFragment, Lifecycle.State.STARTED)
                    .commit()
                CURRENT_SCREEN_ID = R.id.search_stocks
                OLD_SCREEN_ID = R.id.favorite_stocks
                true
            }
            R.id.favorite_stocks -> {
                supportFragmentManager.beginTransaction()
                    .show(favoriteStocksFragment)
                    .hide(searchStocksFragment)
                    .setMaxLifecycle(favoriteStocksFragment, Lifecycle.State.RESUMED)
                    .setMaxLifecycle(searchStocksFragment, Lifecycle.State.STARTED)
                    .commit()

                CURRENT_SCREEN_ID = R.id.favorite_stocks
                OLD_SCREEN_ID = R.id.search_stocks
                true
            }
            else -> false
        }
    }

    private fun showNavigation() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentsContainer, searchStocksFragment)
            .add(R.id.fragmentsContainer, favoriteStocksFragment)
            .hide(favoriteStocksFragment)
            .setMaxLifecycle(searchStocksFragment, Lifecycle.State.RESUMED)
            .setMaxLifecycle(favoriteStocksFragment, Lifecycle.State.STARTED)
            .commit()
    }

    private fun showDisconnectedMessage(show: Boolean) {
        binding.layoutDisconnected.isVisible(!show)
        binding.fragmentsContainer.isVisible(show)
        binding.bottomNavigation.isVisible(show)
    }

    companion object {
        private var CURRENT_SCREEN_ID = R.id.search_stocks
        private var OLD_SCREEN_ID = R.id.favorite_stocks
    }
}