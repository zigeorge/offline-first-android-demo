package com.geo.currencyconverter.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.geo.currencyconverter.data.repository.FakeCurrencyConverterRepository
import com.geo.currencyconverter.ui.composables.CurrencyGridItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyConverterHomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyConverterHomeViewModel
    private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val savedState = SavedStateHandle()
        viewModel = CurrencyConverterHomeViewModel(
            savedState,
            FakeCurrencyConverterRepository()
        )
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify that allcurrency contains empty list initially`() = runTest {
        viewModel.allCurrencies.test {
            val emission = awaitItem()
            assertThat(emission.size).isEqualTo(0)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `verify that allcurrency contains list of currencyData on second emission`() = runTest {
        viewModel.allCurrencies.test {
            awaitItem()
            val secondEmission = awaitItem()
            assertThat(secondEmission.size).isGreaterThan(0)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `verify that currency grid items for USD does not contain USD currency`() = runTest {
        viewModel.currencyGridItems.test {
            awaitItem()
            val emission = awaitItem()
            assertThat(emission).doesNotContain(CurrencyGridItem(label = "USD", value = 1.0))
        }
    }

    @Test
    fun `verify that currency dropdown selected item is USD currency`() = runTest {
        viewModel.allCurrencies.test {
            awaitItem()
            val emission = awaitItem()
            var usdIndex = -1
            for (index in 0..emission.size) {
                if (emission[index].name == "USD") {
                    usdIndex = index
                    break
                }
            }
            assertThat(viewModel.selectedIndex.value).isEqualTo(usdIndex)
        }
    }

    @Test
    fun `verify that amount is initially one`() = runTest {
        viewModel.amount.test {
            val emission = awaitItem()
            assertThat(emission.toAmount()).isEqualTo(1.0)
        }
    }

    @Test
    fun `verify that amount update changes amount`() = runTest {
        viewModel.updateAmount("5.0")
        viewModel.amount.test {
            val emission = awaitItem()
            assertThat(emission.toAmount()).isEqualTo(5.0)
        }
    }

    @Test
    fun `verify that amount update with illegal character always returns one as amount`() = runTest {
        viewModel.updateAmount(".jhgk5.0")
        viewModel.amount.test {
            val emission = awaitItem()
            assertThat(emission.toAmount()).isEqualTo(1.0)
        }
    }

    @Test
    fun `verify that selection update selection method changes selectedIndex`() = runTest {
        viewModel.updateSelection(0)
        viewModel.selectedIndex.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(0)
        }
    }

    /*@Test
    fun `verify that currenciesNotAvailable returns true initially and returns false once viewmodel initialization is completed`() = runTest {
        viewModel.currencyGridItems.test {
            awaitItem()
            var status = viewModel.currenciesNotAvailable()
            assertThat(status).isTrue()
            awaitItem()
            status = viewModel.currenciesNotAvailable()
            assertThat(status).isFalse()
        }
    }*/


}