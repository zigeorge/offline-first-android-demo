package com.geo.currencyconverter.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeApi {

    @GET("latest.json")
    suspend fun getLatestCurrencies(
        @Query("app_id") appId: String
    ): OpenExchangeApiResponse
}

data class OpenExchangeApiResponse(
    val timestamp: Long,
    val base: String,
    val rates: HashMap<String, Double>
)