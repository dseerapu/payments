package com.dharma.paymentsapp.file_screen.repo

import android.content.Context
import com.dharma.paymentsapp.file_screen.FILE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class FileRepoImpl : FileRepo {

    override suspend fun loadPaymentsAsStringFile(context: Context): String {

        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, FILE_NAME)
            if (!file.exists()) return@withContext "" // Return an empty list if the file doesn't exist

            val jsonString = file.readText() // Read the entire file as a string directly using `readText`

            return@withContext jsonString // Convert the JSON string back to a list
        }
    }

}