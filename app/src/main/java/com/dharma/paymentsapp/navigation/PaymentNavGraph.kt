package com.dharma.paymentsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dharma.paymentsapp.file_screen.FileScreenView
import com.dharma.paymentsapp.main.MainScreen


@Composable
fun PaymentNavGraph(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = PaymentNavigationScreens.Main.name) {

        composable(PaymentNavigationScreens.Main.name) {
            MainScreen(navHostController)
        }

        composable(PaymentNavigationScreens.FileScreen.name) {
            FileScreenView(navHostController)
        }

    }
}

enum class PaymentNavigationScreens{
    Main,
    FileScreen
}