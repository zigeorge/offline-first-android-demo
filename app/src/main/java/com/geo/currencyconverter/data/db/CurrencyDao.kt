package com.geo.currencyconverter.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyDataList: List<CurrencyData>)

    @Query("DELETE FROM currency_data")
    suspend fun delete()

    @Query("SELECT * FROM currency_data")
    fun getCurrencyDataAsFlow(): Flow<List<CurrencyData>>

    @Query("SELECT COUNT(*) FROM currency_data")
    fun count(): Int

}