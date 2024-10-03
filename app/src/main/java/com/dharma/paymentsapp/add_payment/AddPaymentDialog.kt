package com.dharma.paymentsapp.add_payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dharma.paymentsapp.R
import com.dharma.paymentsapp.model.Payment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentDialog(
    availablePaymentTypes: List<String>,
    onDismiss: () -> Unit,
    onSave: (Payment) -> Unit
) {
    var selectedType by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var bankAccount by remember { mutableStateOf("") }

    // State for error message
    var errorMessage by remember { mutableStateOf("") }

    // State to control Dropdown visibility
    var expanded by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.add_payment)) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Dropdown for Payment Type Selection
                    // Material3 Dropdown Box
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded } // Toggle dropdown menu
                    ) {
                        // TextField styled dropdown for selecting payment type
                        TextField(
                            value = selectedType.ifEmpty { stringResource(R.string.select_payment_type) },
                            onValueChange = {},
                            readOnly = true, // Making it read-only to prevent manual typing
                            label = { Text(stringResource(R.string.payment_type)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        // Dropdown items
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            availablePaymentTypes.forEach { paymentType ->
                                DropdownMenuItem(
                                    text = { Text(paymentType) },
                                    onClick = {
                                        selectedType = paymentType // Update selected option
                                        expanded = false // Close the dropdown
                                        errorMessage =""
                                    }
                                )
                            }
                        }
                    }

                    // Amount Input Field
                    TextField(
                        value = amount,
                        onValueChange = { amount = it
                                        errorMessage =""},
                        label = { Text(stringResource(R.string.amount)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Transaction Reference Field for Bank Transfer or Credit Card
                    if (selectedType == "Bank Transfer" || selectedType == "Credit Card") {

                        TextField(
                            value = bankAccount,
                            onValueChange = {
                                bankAccount = it
                                errorMessage = "" },
                            label = { Text(stringResource(R.string.provider_icici_citibank_etc)) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        TextField(
                            value = details,
                            onValueChange =
                            {
                                details = it
                                errorMessage = ""
                            },
                            label = { Text(stringResource(R.string.transaction_reference)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Display error message if present
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amountValue = amount.toDoubleOrNull()
                        if ((selectedType == "Bank Transfer" || selectedType == "Credit Card") &&
                            amountValue != null && selectedType.isNotEmpty() &&
                                bankAccount.isNotEmpty() && details.isNotEmpty()) {
                                errorMessage = "" // Clear error message
                                onSave(
                                    Payment(0,
                                        selectedType,
                                        amountValue,
                                        bankAccount.ifEmpty { null },
                                        details.ifEmpty { null }
                                    )
                                )
                        }else if (selectedType == "Cash" &&  amountValue != null && selectedType.isNotEmpty()) {
                            errorMessage = "" // Clear error message
                            onSave(
                                Payment(0,
                                    selectedType,
                                    amountValue,
                                    bankAccount.ifEmpty { null },
                                    details.ifEmpty { null }
                                )
                            )
                        }else{
                            errorMessage = "Please fill all fileds"
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
        )
}

@Preview
@Composable
fun AddPaymentDialogPreview() {
    AddPaymentDialog(
        availablePaymentTypes = listOf("Bank Transfer", "Credit Card"),
        onDismiss = {},
        onSave = { _-> }
    )
}