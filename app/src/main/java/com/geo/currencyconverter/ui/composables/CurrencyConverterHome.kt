package com.geo.currencyconverter.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geo.currencyconverter.R
import com.geo.currencyconverter.domain.network.ConnectivityObserver
import com.geo.currencyconverter.ui.theme.CurrencyConverterTheme
import com.geo.currencyconverter.ui.viewmodels.CurrencyConverterHomeViewModel

@Composable
fun CurrencyConverter(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CurrencyConverterHome()
    }
}



@Composable
fun CurrencyConverterHome(
    modifier: Modifier = Modifier,
    viewModel: CurrencyConverterHomeViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    if (status == null) {
        Column {
            TopBar(modifier = modifier)
            Spacer(modifier = modifier.height(8.dp))
            AmountEntry(modifier = modifier, viewModel)
            Spacer(modifier = modifier.height(8.dp))
            CurrencySelector(modifier = modifier, viewModel)
            Spacer(modifier = modifier.height(8.dp))
            CurrenciesGrid(modifier = modifier, viewModel)
        }
    } else {
        ErrorBox(errorMessage = stringResource(R.string.network_error))
    }
}

@Composable
fun ErrorBox(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = errorMessage)
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    MediumTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        title = {
            Text(
                "Currency Converter",
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Composable
fun AmountEntry(
    modifier: Modifier = Modifier,
    viewModel: CurrencyConverterHomeViewModel
) {
    val amount by viewModel.amount.collectAsStateWithLifecycle()

    TextField(
        value = amount,
        onValueChange = { newText ->
            viewModel.updateAmount(newText)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.primary
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_amount))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(
                horizontal = 8.dp
            )
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    CurrencyConverterTheme {
        TopBar()
    }
}