package com.geo.currencyconverter.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geo.currencyconverter.R
import com.geo.currencyconverter.data.db.CurrencyData
import com.geo.currencyconverter.ui.viewmodels.CurrencyConverterHomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencySelector(
    modifier: Modifier = Modifier,
    viewModel: CurrencyConverterHomeViewModel
) {
    val currencies by viewModel.allCurrencies.collectAsStateWithLifecycle()
    var clicked by remember { mutableStateOf(false) }
    val selectedIndex by viewModel.selectedIndex.collectAsStateWithLifecycle()

    val selectedCurrency = if (currencies.isEmpty()) {
        CurrencyData.defaultCurrency()
    } else {
        currencies[selectedIndex]
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        TextField(
            value = selectedCurrency.name,
            modifier = modifier
                .width(150.dp)
                .clickable {
                    clicked = !clicked
                },
            onValueChange = {},
            readOnly = true,
            enabled = false,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                disabledTextColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = {
                val icon = if (clicked) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        )

        if (clicked) {
            Dialog(
                onDismissRequest = { clicked = false },
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    modifier = modifier
                        .padding(vertical = 20.dp, horizontal = 20.dp)
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = Dp.Infinity)
                ) {
                    val listState = rememberLazyListState()
                    if (currencies[selectedIndex].name != CurrencyData.defaultCurrency().name) {
                        LaunchedEffect("ScrollToSelected") {
                            listState.scrollToItem(index = selectedIndex)
                        }
                    }

                    LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                        stickyHeader {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .fillMaxWidth()
                                    .height(40.dp),
                            ) {
                                Text(
                                    stringResource(id = R.string.select_currency),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                        itemsIndexed(currencies) { index, item ->
                            TextField(
                                value = item.name,
                                onValueChange = {},
                                readOnly = true,
                                enabled = false,
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    disabledTextColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        clicked = !clicked
                                        viewModel.updateSelection(index)
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}
