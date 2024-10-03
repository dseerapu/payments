package com.dharma.paymentsapp.ui.theme.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dharma.paymentsapp.model.Payment

@Composable
fun PaymentChip(payment: Payment,
                onRemove: () -> Unit) {

    InputChip(
        label = { Text(text = "${payment.type}: Rs. ${payment.amount}") },
        onClick = { onRemove.invoke() },
        selected = true,
        trailingIcon = {
                Icon(Icons.Default.Close, contentDescription = "Remove Payment")
        }
    )
}

@Preview
@Composable
fun PaymentChipPreview() {
    PaymentChip(
        payment = Payment(1, "Bank Transfer", 100.0, "Ref123"),
        onRemove = {}
    )
}