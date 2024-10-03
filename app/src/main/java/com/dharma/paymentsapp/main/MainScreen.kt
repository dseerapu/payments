package com.dharma.paymentsapp.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dharma.paymentsapp.ui.theme.widgets.PaymentChip
import com.dharma.paymentsapp.PaymentViewModel
import com.dharma.paymentsapp.R
import com.dharma.paymentsapp.add_payment.AddPaymentDialog
import com.dharma.paymentsapp.navigation.PaymentNavigationScreens
import com.dharma.paymentsapp.ui.theme.PaymentsAppTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    viewModel : PaymentViewModel = hiltViewModel()
) {

    var showAddPaymentDialog by remember{mutableStateOf(false)}

    val payments by viewModel.payments.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState()
    val context = LocalContext.current

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        viewModel.fetchData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.payment_transactions)) })
        },
        content = { paddingValues -> // handle padding values from Scaffold

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding from Scaffold
                    .padding(16.dp)
            ) {

                if(payments.isEmpty()){

                    NoPaymentTransactionsView(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }else{
                    // Use LazyColumn to list payments
                    LazyColumn(
                        modifier = Modifier.weight(1f) // Use weight to push buttons to the bottom
                    ) {
                        items(payments, key = {payment -> payment.id}) { payment ->
                            PaymentChip(payment, onRemove = { viewModel.removePayment(payment) })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if(payments.isNotEmpty()){

                    //No Payment Transaction view
                    TotalAmountView(
                        totalAmount,
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons at the bottom
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            showAddPaymentDialog = true
                        }
                    ) {
                        Text(stringResource(R.string.add_payment))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.savePayments(context)
                            coroutineScope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.payment_transactions_are_stored_in_payments_txt_file),
                                    actionLabel = context.getString(R.string.open_file)
                                )

                                if(result==SnackbarResult.ActionPerformed){
                                    navHostController.navigate(PaymentNavigationScreens.FileScreen.name)
                                }
                            } },
                    ) {
                        Text(stringResource(R.string.save_payments))
                    }
                }

                if(showAddPaymentDialog) {
                    AddPaymentDialog(
                        availablePaymentTypes = viewModel.getAvailablePaymentTypes(),
                        onDismiss = {
                           showAddPaymentDialog = false },
                        onSave = { payment ->
                            viewModel.addPayment(payment)
                            showAddPaymentDialog = false
                        }
                    )
                }

                // Display the Snackbar
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier  = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                )
            }


        }
    )
}

@Composable
private fun NoPaymentTransactionsView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.no_payments_added_yet) +
                    stringResource(R.string.click_on_add_payment_button_to_add_a_payment_transaction),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

@Composable
fun TotalAmountView(totalAmount: Double, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.total_amount_rs, totalAmount),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Preview
@Composable
fun NoPaymentTransactionsViewPreview(){
    PaymentsAppTheme {
        NoPaymentTransactionsView()
    }
}

@Preview
@Composable
fun TotalAmountViewPreview(){
    PaymentsAppTheme{
        TotalAmountView(totalAmount = 100.0)
    }

}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navHostController = NavHostController(LocalContext.current))
}
