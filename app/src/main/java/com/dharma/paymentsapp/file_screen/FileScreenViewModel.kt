package com.dharma.paymentsapp.file_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dharma.paymentsapp.file_screen.repo.FileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileScreenViewModel
@Inject constructor(
    private val fileRepo: FileRepo
) : ViewModel() {

    private val _jsonData = MutableStateFlow("")
    val jsonData: StateFlow<String> = _jsonData

    fun fetchJsonData(context : Context){
        viewModelScope.launch {
            _jsonData.value = fileRepo.loadPaymentsAsStringFile(context)
        }
    }
}