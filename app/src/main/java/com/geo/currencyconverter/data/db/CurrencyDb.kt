package com.geo.currencyconverter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CurrencyData::class],
    version =1,
    exportSchema = false
)
abstract class CurrencyDb: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}