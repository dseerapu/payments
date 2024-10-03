package com.dharma.paymentsapp.di

import com.dharma.paymentsapp.db.PaymentsDao
import com.dharma.paymentsapp.file_screen.repo.FileRepo
import com.dharma.paymentsapp.file_screen.repo.FileRepoImpl
import com.dharma.paymentsapp.main.repository.MainRepository
import com.dharma.paymentsapp.main.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMainRepository(paymentsDao: PaymentsDao): MainRepository {
        return MainRepositoryImpl(paymentsDao)
    }

    @Provides
    fun providesFileRepository(): FileRepo {
        return FileRepoImpl()
    }
}