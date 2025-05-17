package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SurveyScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Günlük ulaşım tercihiniz nedir?", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        listOf("Yürüyüş", "Toplu Taşıma", "Kendi Aracım", "Bisiklet").forEach { option ->
            Button(
                onClick = { selectedOption = option },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = if (selectedOption == option) ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ) else ButtonDefaults.buttonColors()
            ) {
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Seçilen veriyi kaydedebilirsin
                navController.navigate("home") // ana sayfaya yönlendirme
            },
            enabled = selectedOption != null
        ) {
            Text("Devam Et")
        }
    }
}
