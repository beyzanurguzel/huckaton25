package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.presentation.screens.LoginScreen
import com.example.myapplication.presentation.screens.SelectionScreen
import com.example.myapplication.presentation.screens.SurveyScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "selection") {
        composable("selection") {
            SelectionScreen(navController)
        }

        composable(
            "login/{type}",
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "individual"
            LoginScreen(navController = navController, type = type) // ✅ düzeltildi
        }

        composable("survey") {
            SurveyScreen(navController)
        }
    }
}
