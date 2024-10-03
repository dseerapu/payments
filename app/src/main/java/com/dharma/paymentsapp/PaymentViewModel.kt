package com.dharma.paymentsapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharma.paymentsapp.main.repository.MainRepository
import com.dharma.paymentsapp.model.Payment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repo: MainRepository
) : ViewModel() {
    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments

    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double> = _totalAmount

    init {
        viewModelScope.launch {
            repo.getAllPayments().collectLatest { payments ->
                _payments.value = payments
                calculateTotalAmount()
            }
        }
    }

    fun fetchData(context: Context) {

        viewModelScope.launch {

            val filePayments = repo.loadPaymentsFromFile(context)

            if (filePayments.isNotEmpty()) {

                repo.deleteAllPaymentsInDB().also {
                    repo.insertPayments(filePayments)
                }

                calculateTotalAmount()

            } else {

                // If no file data, fetch from DB
                repo.getAllPayments().collectLatest { payments ->
                    _payments.value = payments
                    calculateTotalAmount()
                }
            }
        }

    }

    fun addPayment(payment: Payment) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertPayment(payment)
            calculateTotalAmount()
        }
    }

    fun removePayment(payment: Payment) {
       viewModelScope.launch(Dispatchers.IO) {
           repo.deletePaymentById(payment.id)
           calculateTotalAmount()
       }
    }

    private fun calculateTotalAmount() {
        _totalAmount.value = _payments.value.sumOf { it.amount }
    }

    fun savePayments(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.savePaymentsToFile(context, payments.value)
        }
    }

    fun getAvailablePaymentTypes(): List<String> {
        val addedTypes = payments.value.map { it.type }
        return listOf("Cash", "Bank Transfer", "Credit Card").filter { it !in addedTypes }
    }
}