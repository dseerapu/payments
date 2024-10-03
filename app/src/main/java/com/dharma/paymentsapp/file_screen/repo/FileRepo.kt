package com.dharma.paymentsapp.file_screen.repo

import android.content.Context

interface FileRepo {

    suspend fun loadPaymentsAsStringFile(context: Context): String

}