package com.example.mystocks.favorite_stocks

import com.example.mystocks.StockInfoRepository
import com.example.mystocks.api.StocksInfoResponseType
import com.example.mystocks.mapper.StockMapper
import com.example.mystocks.model.StockModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class FavoriteStocksInteractorImplTest {

    private lateinit var repositoryMock: StockInfoRepository

    private val mapper = StockMapper()

    private lateinit var interactor: FavoriteStocksInteractorImpl

    @Before
    fun setUp() {

        repositoryMock = mock {
            on { getAllFavoriteLocalStocks() } doReturn Single.just(favoriteLocalStocksList)
            on { getSpecificStocksList(any()) } doReturn Single.just(stocksList)
        }

        interactor = FavoriteStocksInteractorImpl(repositoryMock, mapper)
    }

    @Test
    fun getCorrectFavoriteStocksList() {

        val result = interactor.getFavoriteStocksList()

        result.test()
    }

    private val favoriteLocalStocksList = listOf<String>("UNH", "GS", "HD")

    private val stocksList = listOf<StocksInfoResponseType>(
        StocksInfoResponseType(
            longName = "UnitedHealth Group Incorporated",
            symbol = "UNH",
            price = 396.87f,
            currency = "USD",
            change = -1.17001f,
            changePercent = -0.299229f,
            isFavorite = false
        ),
        StocksInfoResponseType(
            longName = "The Goldman Sachs Group, Inc.",
            symbol = "GS",
            price = 346.19f,
            currency = "USD",
            change = 0.779999f,
            changePercent = 0.227863f,
            isFavorite = false
        ),
        StocksInfoResponseType(
            longName = "The Home Depot, Inc.",
            symbol = "HD",
            price = 445.32f,
            currency = "USD",
            change = -1.22998f,
            changePercent = -0.374903f,
            isFavorite = false
        ),
        StocksInfoResponseType(
            longName = "Microsoft Corporation",
            symbol = "MSFT",
            price = 190.67f,
            currency = "USD",
            change = -2f,
            changePercent = -0.76704764f,
            isFavorite = false
        ),
        StocksInfoResponseType(
            longName = "Amgen Inc.",
            symbol = "AMGN",
            price = 1009.23f,
            currency = "USD",
            change = 0.2599945f,
            changePercent = 0.10167553f,
            isFavorite = false
        )
    )
}