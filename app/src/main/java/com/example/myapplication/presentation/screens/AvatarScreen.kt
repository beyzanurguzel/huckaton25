package com.example.myapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.SurveyViewModel

@Composable
fun AvatarScreen(
    navController: NavController,
    viewModel: SurveyViewModel
) {
    val score = viewModel.calculateScore()

    val avatarOptions = when {
        score >= 80 -> listOf("🌱 Doğa Dostu", "🦸 Yeşil Süper Kahraman", "⚡ Enerji Koruyucusu")
        score >= 50 -> listOf("🌍 Çevre Bilinçli", "📗 Sürdürülebilirlik Öğrencisi")
        else -> listOf("🧭 Farkındalık Yolcusu", "🌑 Yeni Başlayan")
    }

    var selectedAvatar by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Toplam Puan: $score", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sana Özel Avatarını Seç!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        avatarOptions.forEach { avatar ->
            val isSelected = selectedAvatar == avatar

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                    .clickable { selectedAvatar = avatar }
                    .padding(16.dp)
            ) {
                Text(
                    text = avatar,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Seçilen avatarı saklayabiliriz (SharedPreferences vs.)
                navController.navigate("character") {
                    popUpTo("avatar") { inclusive = true }
                }
            },
            enabled = selectedAvatar != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Avatarı Onayla ve Başla!")
        }
    }
}
