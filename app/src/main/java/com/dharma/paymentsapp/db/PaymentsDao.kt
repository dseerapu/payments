package com.dharma.paymentsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dharma.paymentsapp.model.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentsDao {

    @Query("SELECT * FROM payments")
    fun getAllPayments(): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE id = :id")
    fun getPaymentById(id: Int): Payment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayments(payments: List<Payment>)

    @Query("DELETE FROM payments WHERE id = :id")
    suspend fun deletePaymentById(id: Int)

    @Query("DELETE FROM payments")
    suspend fun deleteAllPayments()
}
