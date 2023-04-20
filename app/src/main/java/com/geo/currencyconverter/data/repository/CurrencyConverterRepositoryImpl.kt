package com.geo.currencyconverter.data.repository

import android.content.SharedPreferences
import com.geo.currencyconverter.BuildConfig
import com.geo.currencyconverter.data.db.CurrencyDao
import com.geo.currencyconverter.data.db.CurrencyData
import com.geo.currencyconverter.data.network.OpenExchangeApi
import com.geo.currencyconverter.other.Constants.LAST_UPDATED_TIME
import com.geo.currencyconverter.other.Constants.UPDATE_INTERVAL
import com.geo.currencyconverter.other.timeDiffFromNowInMinute
import com.geo.currencyconverter.other.toCurrencyDataList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
    private val openExchangeApi: OpenExchangeApi,
    private val sharedPref: SharedPreferences
): CurrencyConverterRepository {

    override suspend fun updateCurrencies() {
        val lastUpdated = sharedPref.getLong(LAST_UPDATED_TIME, 0)
        if (timeDiffFromNowInMinute(lastUpdated) > UPDATE_INTERVAL) {
            val latestCurrencies = openExchangeApi.getLatestCurrencies(BuildConfig.APP_ID)
            currencyDao.delete()
            currencyDao.insertAll(latestCurrencies.toCurrencyDataList())
            with(sharedPref.edit()) {
                putLong(LAST_UPDATED_TIME, System.currentTimeMillis())
                apply()
            }
        }
    }

    override suspend fun getAllCurrencyData(): Flow<List<CurrencyData>> {
        try {
            updateCurrencies()
            return currencyDao.getCurrencyDataAsFlow()
        } catch (ex: Exception) {
            throw ex
        }
    }

}