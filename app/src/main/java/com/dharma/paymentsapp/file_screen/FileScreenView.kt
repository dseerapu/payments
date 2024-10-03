package com.dharma.paymentsapp.file_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dharma.paymentsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreenView(
    navController: NavController,
    viewModel: FileScreenViewModel = hiltViewModel(),
) {

    val jsonData by viewModel.jsonData.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchJsonData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.payment_transactions_file)) },
                navigationIcon ={
                    androidx.compose.material3.IconButton(onClick = {
                        navController.popBackStack()
                    }){
                        androidx.compose.material3.Icon(
                            androidx.compose.material.icons.Icons.Default.Close,
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                }
            )
        },
        content = { paddingValues -> // handle padding values from Scaffold

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding from Scaffold
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    // Show the JSON data in a TextArea
                    Text(
                        text = jsonData,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f) // Take up available space
                    )
                }
            }
        }
    )
}