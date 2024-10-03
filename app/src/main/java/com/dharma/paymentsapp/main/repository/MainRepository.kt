package com.dharma.paymentsapp.main.repository

import android.content.Context
import com.dharma.paymentsapp.model.Payment
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllPayments(): Flow<List<Payment>>
    suspend fun insertPayment(payment: Payment)
    suspend fun deletePaymentById(id: Int)
    suspend fun deleteAllPaymentsInDB()
    suspend fun insertPayments(payments : List<Payment>)
    suspend fun savePaymentsToFile(context : Context, payments: List<Payment>)
    suspend fun loadPaymentsFromFile(context: Context): List<Payment>
}