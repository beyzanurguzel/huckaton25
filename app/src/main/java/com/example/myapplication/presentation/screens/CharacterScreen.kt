package com.example.myapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.SurveyViewModel
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R
import androidx.compose.ui.layout.ContentScale

@Composable
fun CharacterScreen(
    navController: NavController,
    viewModel: SurveyViewModel
) {
    val score = viewModel.calculateScore()
    val avatar = remember { viewModel.answers.values.flatten().lastOrNull() ?: "?" }

    val avatarImageRes = when (avatar.lowercase()) {
        "enerji tasarrufu", "doğa dostu"         -> R.drawable.lego1
        "toplu taşıma", "sürdürülebilirlik öğrencisi" -> R.drawable.lego2
        "geri dönüşüm", "su kullanımı"           -> R.drawable.lego3
        "karbon ayak izi azaltımı", "farkındalık yolcusu" -> R.drawable.lego4
        else                                     -> R.drawable.lego1
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Karakterin Hazır!", /* … */)

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


        Spacer(modifier = Modifier.height(16.dp))

        Text("Seçilen Avatar", fontWeight = FontWeight.Bold)
        Text(avatar)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Toplam Puan", fontWeight = FontWeight.Bold)
        Text("$score 🌿")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("selection") {
                    popUpTo("selection") { inclusive = true }
                }
            }
        ) {
            Text("Ana Sayfaya Dön")
        }
    }
}
