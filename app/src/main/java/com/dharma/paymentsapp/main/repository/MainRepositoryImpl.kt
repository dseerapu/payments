package com.dharma.paymentsapp.main.repository

import android.content.Context
import com.dharma.paymentsapp.db.PaymentsDao
import com.dharma.paymentsapp.file_screen.FILE_NAME
import com.dharma.paymentsapp.model.Payment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.reflect.Type
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private  val paymentsDao: PaymentsDao
) : MainRepository {

    override fun getAllPayments(): Flow<List<Payment>> =
        paymentsDao.getAllPayments()


    override suspend fun insertPayment(payment: Payment)  =
        paymentsDao.insertPayment(payment)

    override suspend fun deletePaymentById(id: Int) =
        paymentsDao.deletePaymentById(id)

    override suspend fun deleteAllPaymentsInDB() = paymentsDao.deleteAllPayments()

    override suspend fun insertPayments(payments: List<Payment>) =
        paymentsDao.insertPayments(payments)

    override suspend fun savePaymentsToFile(context: Context, payments: List<Payment>) {
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val paymentsJsonString = gson.toJson(payments) // Convert payments list to JSON string

            val file = File(context.filesDir, FILE_NAME)
            file.writeText(paymentsJsonString) // Write the JSON string directly to the file
        }
    }

    override suspend fun loadPaymentsFromFile(context: Context): List<Payment> {
        return withContext(Dispatchers.IO) {
                val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return@withContext emptyList<Payment>() // Return an empty list if the file doesn't exist

            val jsonString = file.readText() // Read the entire file as a string directly using `readText`

            val gson = Gson()
            val listType: Type = object : TypeToken<List<Payment>>() {}.type
            return@withContext gson.fromJson(jsonString, listType) // Convert the JSON string back to a list
        }
    }
}