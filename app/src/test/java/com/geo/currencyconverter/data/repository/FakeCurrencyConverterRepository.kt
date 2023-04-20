package com.geo.currencyconverter.data.repository

import com.geo.currencyconverter.data.api.FakeOpenExchangeApiTest
import com.geo.currencyconverter.data.db.CurrencyData
import com.geo.currencyconverter.other.toCurrencyDataList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCurrencyConverterRepository(private val fakeOpenExchangeApiTest: FakeOpenExchangeApiTest = FakeOpenExchangeApiTest()) :
    CurrencyConverterRepository {
    private val fakeCurrencyData = ArrayList<CurrencyData>()

    override suspend fun updateCurrencies() {
        var fakeId = 1;
        fakeOpenExchangeApiTest
            .getLatestCurrencies("TEST")
            .toCurrencyDataList()
            .map {
                fakeCurrencyData.add(it.copy(id = fakeId++))
            }
    }

    override suspend fun getAllCurrencyData(): Flow<List<CurrencyData>> {
        updateCurrencies()
        return flowOf(fakeCurrencyData)
    }
}

