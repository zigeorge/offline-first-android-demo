package com.geo.currencyconverter.data.repository

import com.geo.currencyconverter.data.db.CurrencyData
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterRepository {
    suspend fun updateCurrencies()

    suspend fun getAllCurrencyData(): Flow<List<CurrencyData>>
}