package com.dharma.paymentsapp.di

import android.app.Application
import androidx.room.Room
import com.dharma.paymentsapp.db.PaymentsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun providesPaymentsDB(application: Application): PaymentsDB {
        return Room.databaseBuilder(
            application,
            PaymentsDB::class.java,
            "payments_db"
        ).build()
    }

    @Provides
    fun paymentsDao(paymentsDB: PaymentsDB) = paymentsDB.paymentsDao
}