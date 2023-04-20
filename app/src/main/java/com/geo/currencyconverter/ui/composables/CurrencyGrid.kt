package com.geo.currencyconverter.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geo.currencyconverter.ui.theme.CurrencyConverterTheme
import com.geo.currencyconverter.ui.viewmodels.CurrencyConverterHomeViewModel


class CurrencyGridItem(
    val label: String,
    var value: Double,
) {
    fun showValue(): String {
        return String.format("%.3f", value)
    }

    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return label.hashCode() + value.hashCode()
    }
}

@Composable
fun CurrencyCard(
    modifier: Modifier = Modifier,
    currencyItem: CurrencyGridItem
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = currencyItem.label,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.paddingFromBaseline(bottom = 4.dp)
            )
            Text(
                text = currencyItem.showValue(),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.paddingFromBaseline(bottom = 4.dp)
            )
        }
    }
}

@Composable
fun CurrenciesGrid(
    modifier: Modifier = Modifier,
    viewModel: CurrencyConverterHomeViewModel
) {
    val items by viewModel.currencyGridItems.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(items) { item ->
            CurrencyCard(modifier = modifier, item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyCardPreview() {
    CurrencyConverterTheme {
        CurrencyCard(currencyItem = CurrencyGridItem("USD", 1.00))
    }
}