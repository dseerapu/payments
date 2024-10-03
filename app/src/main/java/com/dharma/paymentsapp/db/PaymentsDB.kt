package com.dharma.paymentsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dharma.paymentsapp.model.Payment

@Database(
    entities = [Payment::class],
    version = 1
)
abstract class PaymentsDB : RoomDatabase() {

    abstract val paymentsDao : PaymentsDao
}