package com.example.myapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewmodel.SurveyViewModel
import androidx.compose.ui.layout.ContentScale

@Composable
fun CharacterScreen(
    navController: NavController,
    viewModel: SurveyViewModel
) {
    val score = viewModel.calculateScore()
    val selectedAvatar = remember { viewModel.answers.values.flatten().lastOrNull() ?: "default" }

    val avatarImageRes = when (selectedAvatar.lowercase()) {
        "enerji tasarrufu", "doğa dostu" ->
            R.drawable.lego1
        "toplu taşıma", "sürdürülebilirlik öğrencisi" ->
            R.drawable.lego2
        "geri dönüşüm", "su kullanımı" ->
            R.drawable.lego3
        "karbon ayak izi azaltımı", "farkındalık yolcusu" ->
            R.drawable.lego4
        else ->
            R.drawable.lego1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Karakterin Hazır!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Image(
                painter = painterResource(id = avatarImageRes),
                contentDescription = "Seçilen Avatar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = selectedAvatar.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Skorun: $score 🌿",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo("selection") { inclusive = true }
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("Ana Sayfaya Git")
        }
    }
}
