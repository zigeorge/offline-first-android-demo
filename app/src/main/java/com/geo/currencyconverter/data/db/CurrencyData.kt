package com.geo.currencyconverter.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geo.currencyconverter.other.Constants.CURRENCY_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = CURRENCY_TABLE
)
data class CurrencyData(
    val name: String,
    val value: Double,
    val base: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Parcelable {
    companion object {
        fun defaultCurrency(): CurrencyData {
            return CurrencyData(
                name = "USD",
                value = 1.0,
                base = 1.0
            )
        }
    }
}


