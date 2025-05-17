package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.SurveyViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SurveyViewModel = viewModel()
) {
    val score = viewModel.calculateScore()

    // Basit öneri listesi (survey sonuçlarına göre dinamik üretilebilir)
    val tips = listOf(
        "Günlük 10 dk yürüyüş yaparak enerji tasarrufu yapın.",
        "Evinizdeki ampulleri LED ile değiştirin.",
        "Haftada bir günü araçsız geçirip karbon ayak izinizi düşürün."
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Ana Sayfa") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Merhaba!", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("Toplam Puanınız: $score", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))

            Text("Önerileriniz", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(tips) { tip ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            tip,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("stats") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("İstatistiklere Git")
            }
        }
    }
}
