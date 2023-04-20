package com.geo.currencyconverter.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.geo.currencyconverter.R
import com.geo.currencyconverter.data.db.CurrencyDao
import com.geo.currencyconverter.data.db.CurrencyDb
import com.geo.currencyconverter.data.network.OpenExchangeApi
import com.geo.currencyconverter.data.network.RetrofitInstance
import com.geo.currencyconverter.data.repository.CurrencyConverterRepository
import com.geo.currencyconverter.data.repository.CurrencyConverterRepositoryImpl
import com.geo.currencyconverter.domain.network.ConnectivityObserver
import com.geo.currencyconverter.domain.network.NetworkConnectivityObserver
import com.geo.currencyconverter.other.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiClient(): OpenExchangeApi {
        return RetrofitInstance.api
    }

    @Provides
    fun provideDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CurrencyDb::class.java, DB_NAME).build()

    @Provides
    fun provideCurrencyDao(
        db: CurrencyDb
    ) = db.currencyDao()

    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.pref_key), Context.MODE_PRIVATE)

    @Provides
    fun provideCurrencyConverterRepository(
        dao: CurrencyDao,
        api: OpenExchangeApi,
        preferences: SharedPreferences
    ): CurrencyConverterRepository = CurrencyConverterRepositoryImpl(dao, api, preferences)

    @Provides
    fun provideNetworkObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver = NetworkConnectivityObserver(context)
}