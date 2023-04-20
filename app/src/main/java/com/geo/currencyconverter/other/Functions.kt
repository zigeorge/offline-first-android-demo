package com.geo.currencyconverter.other

import com.geo.currencyconverter.data.db.CurrencyData
import com.geo.currencyconverter.data.network.OpenExchangeApiResponse
import java.util.concurrent.TimeUnit

fun timeDiffFromNowInMinute(from: Long): Long {
    val currentTime = System.currentTimeMillis()
    return TimeUnit.MILLISECONDS.toMinutes(currentTime - from)
}

fun OpenExchangeApiResponse.toCurrencyDataList(): List<CurrencyData> {
    val currencyDataList = ArrayList<CurrencyData>()
    rates.forEach { (currencyName, currencyValue) ->
        currencyDataList.add(
            CurrencyData(
                name = currencyName,
                value = currencyValue,
                base = 1/currencyValue
            )
        )
    }
    return currencyDataList
}