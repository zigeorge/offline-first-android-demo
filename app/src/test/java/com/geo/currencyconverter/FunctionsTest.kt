package com.geo.currencyconverter

import com.geo.currencyconverter.data.api.FakeOpenExchangeApiTest
import com.geo.currencyconverter.other.timeDiffFromNowInMinute
import com.geo.currencyconverter.other.toCurrencyDataList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FunctionsTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test time diff from now in minute returns difference in minute `() {
        val thirtyOneMinutesAgoInMillis = System.currentTimeMillis() - (31 * 60 * 1000)
        val diff = timeDiffFromNowInMinute(thirtyOneMinutesAgoInMillis)
        assertThat(diff).isEqualTo(31)
    }

    @Test
    fun `test OpenExchangeApiResponse to currency data list`() = runTest {
        val mockOpenExchangeResponse = FakeOpenExchangeApiTest().getLatestCurrencies("")
        val currencyDataList = mockOpenExchangeResponse.toCurrencyDataList()
        assertThat(currencyDataList.size).isEqualTo(69)
        assertThat(currencyDataList[68].name).isEqualTo("USD")
    }
}