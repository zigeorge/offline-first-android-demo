package com.geo.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.geo.currencyconverter.domain.network.ConnectivityObserver
import com.geo.currencyconverter.domain.network.NetworkConnectivityObserver
import com.geo.currencyconverter.ui.composables.CurrencyConverter
import com.geo.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
//    private lateinit var connectivityObserver: ConnectivityObserver
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            CurrencyConverterTheme {
//                val status by connectivityObserver.observe().collectAsState(
//                    initial = ConnectivityObserver.Status.Available
//                )
                CurrencyConverter()
            }
        }
    }
}
