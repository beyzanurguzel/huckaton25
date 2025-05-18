package com.example.myapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewmodel.SurveyViewModel
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.remember
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: SurveyViewModel,
    openDrawer: () -> Unit
) {
    val score = viewModel.calculateScore()
    // Seçilen avatar ismi
    val selectedAvatar = remember { viewModel.answers.values.flatten().lastOrNull() ?: "default" }
    // Avatar görseli eşleme (lego1..lego4 gibi)
    val avatarImageRes = when (selectedAvatar.lowercase()) {
        "enerji tasarrufu", "doğa dostu" -> R.drawable.lego1
        "toplu taşıma", "sürdürülebilirlik öğrencisi" -> R.drawable.lego2
        "geri dönüşüm", "su kullanımı" -> R.drawable.lego3
        "karbon ayak izi azaltımı", "farkındalık yolcusu" -> R.drawable.lego4
        else -> R.drawable.lego1
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ana Sayfa") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menü")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Karakter görseli
            Card(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Image(
                    painter = painterResource(id = avatarImageRes),
                    contentDescription = "Seçilen Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Skoru göster
            Text(
                text = "Skor: $score 🌿",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))
            // Öneriler başlığı
            Text("Önerileriniz", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            // Öneri kartları
            val tips = listOf(
                "Günlük 10 dk yürüyüş yaparak enerji tasarrufu yapın.",
                "Evinizdeki ampulleri LED ile değiştirin.",
                "Haftada bir günü araçsız geçirip karbon ayak izinizi düşürün."
            )
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                tips.forEach { tip ->
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

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate("stats") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("İstatistiklere Git")
            }
        }
    }
}
