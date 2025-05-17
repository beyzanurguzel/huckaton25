package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.presentation.screens.*
import com.example.myapplication.presentation.viewmodel.SurveyViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    // 🔹 Ortak ViewModel burada bir defa oluşturuluyor
    val viewModel: SurveyViewModel = viewModel()

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

        // İlk anket ekranı
        composable("survey_step/1") {
            SurveyStepScreen(navController = navController, questionId = 1, viewModel = viewModel)
        }

        // Dinamik anket soruları
        composable(
            route = "survey_step/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 1
            SurveyStepScreen(navController = navController, questionId = id, viewModel = viewModel)
        }

        // Anket sonucu ekranı
        composable("survey_result") {
            SurveyResultScreen(navController = navController, viewModel = viewModel)
        }

        // Avatar seçimi ekranı
        composable("avatar") {
            AvatarScreen(navController = navController, viewModel = viewModel)
        }

        composable("character") {
            CharacterScreen(navController = navController, viewModel = viewModel)
        }

    }
}
