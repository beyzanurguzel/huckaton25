package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.SurveyViewModel

@Composable
fun SurveyResultScreen(
    navController: NavController,
    viewModel: SurveyViewModel // 🔹 dışarıdan geldi
) {
    val score = viewModel.calculateScore()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Anket Tamamlandı!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Toplam Puanınız: $score", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate("avatar")
        }) {
            Text("Devam Et")
        }
    }
}
