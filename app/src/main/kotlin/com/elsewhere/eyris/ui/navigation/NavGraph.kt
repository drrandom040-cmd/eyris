package com.elsewhere.eyris.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "auth"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("auth") {
            // Auth screen will be handled by the main activity
        }

        composable("main") {
            // Main app navigation with bottom nav
        }

        composable("dashboard") {
            // Dashboard screen
        }

        composable("search") {
            // Search screen
        }

        composable("leads") {
            // Leads and Contacted screen
        }

        composable("business_detail/{businessId}") { backStackEntry ->
            val businessId = backStackEntry.arguments?.getString("businessId")
            // Business detail screen
        }

        composable("settings") {
            // Settings screen
        }

        composable("export") {
            // Export screen
        }
    }
}
