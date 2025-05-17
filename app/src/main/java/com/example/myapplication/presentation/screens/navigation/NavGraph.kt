package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.presentation.screens.*

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
            LoginScreen(navController = navController, type = type)
        }

        // Login sonrası ilk soru ekranına geçiş
        composable("survey_step/1") {
            SurveyStepScreen(navController = navController, questionId = 1)
        }

        // Diğer adımlar için dinamik soru yönlendirme
        composable(
            route = "survey_step/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 1
            SurveyStepScreen(navController = navController, questionId = id)
        }

        // Anket bitince gösterilecek ekran
        composable("survey_result") {
            SurveyResultScreen(navController)
        }
    }
}
