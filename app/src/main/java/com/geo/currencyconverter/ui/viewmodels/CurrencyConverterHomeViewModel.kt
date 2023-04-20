package com.geo.currencyconverter.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geo.currencyconverter.data.db.CurrencyData
import com.geo.currencyconverter.data.repository.CurrencyConverterRepository
import com.geo.currencyconverter.domain.network.ConnectivityObserver
import com.geo.currencyconverter.ui.composables.CurrencyGridItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterHomeViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle,
    repository: CurrencyConverterRepository,
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _allCurrencies = MutableStateFlow<List<CurrencyData>>(emptyList())
    val allCurrencies = _allCurrencies.asStateFlow()

    val selectedIndex = savedStateHandle.getStateFlow(SELECTED_INDEX, 0)
    val amount = savedStateHandle.getStateFlow(AMOUNT_ENTERED, "")

    private val _status = MutableStateFlow<ConnectivityObserver.Status?>(null)
    val status = _status.asStateFlow()

    private val connectivityStatus = connectivityObserver.observe().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ConnectivityObserver.Status.Lost
    )

    val currencyGridItems =
        combine(_allCurrencies, selectedIndex, amount) { currencies, index, input ->
            if (currencies.isNotEmpty()) {
                val selectedCurrency =
                    currencies[index].copy(base = currencies[index].base * input.toAmount())
                val currencyGridItems = getCurrencyGridItems(currencies, selectedCurrency)
                currencyGridItems
            } else emptyList()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            connectivityStatus.collectLatest { cStatus ->
                if (cStatus == ConnectivityObserver.Status.Available) {
                    repository.getAllCurrencyData().catch {
                        _status.emit(cStatus)
                    }.collect { currencyData ->
                        updateCurrencyData(currencyData)
                    }
                } else {
                    _status.emit(cStatus)
                }
            }
        }
    }

    private suspend fun updateCurrencyData(currencyData: List<CurrencyData>) {
        var index = 0
        for (i in currencyData.indices) {
            if (currencyData[i].name == CurrencyData.defaultCurrency().name) {
                index = i
                break
            }
        }
        savedStateHandle[SELECTED_INDEX] = index
        savedStateHandle[AMOUNT_ENTERED] = ""
        _allCurrencies.emit(currencyData)
        _status.emit(null)
    }

    private fun getCurrencyGridItems(
        currencies: List<CurrencyData>,
        selected: CurrencyData
    ): ArrayList<CurrencyGridItem> {
        val currencyGridItems = ArrayList<CurrencyGridItem>()
        currencies.map { currencyData ->
            if (currencyData.id == selected.id) {
                return@map
            }
            val value = selected.base * currencyData.value
            currencyGridItems.add(
                CurrencyGridItem(
                    label = currencyData.name,
                    value = value
                )
            )
        }
        return currencyGridItems
    }

    fun updateSelection(index: Int) {
        savedStateHandle[SELECTED_INDEX] = index
    }

    fun updateAmount(amount: String) {
        savedStateHandle[AMOUNT_ENTERED] = amount
    }

    /*fun currenciesNotAvailable(): Boolean {
        return currencyGridItems.value.isEmpty()
    }*/

    companion object {
        private const val SELECTED_INDEX = "SELECTED_INDEX"
        private const val AMOUNT_ENTERED = "AMOUNT_ENTERED"
    }
}

fun String.toAmount(): Double {
    if (this.isEmpty()) {
        return 1.0
    }
    return toDoubleOrNull() ?: 1.0
}