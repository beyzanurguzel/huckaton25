package com.example.myapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewmodel.SurveyViewModel
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantScreen(
    navController: NavController,
    surveyViewModel: SurveyViewModel = viewModel()
) {
    // 1) ViewModel'deki score StateFlow'unu izleyelim
    val plantScore by surveyViewModel.score.collectAsState()

    // 2) Geçerli skoru 0..100 aralığına kısıtla
    val safeScore = plantScore.coerceIn(0, 100)

    // 3) Puan aralığına göre drawable seçimi
    val plantRes = when (safeScore) {
        in 0..24   -> R.drawable.plant4
        in 25..49  -> R.drawable.plant1
        in 50..74  -> R.drawable.plant2
        else       -> R.drawable.plant3
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bitki Durumun") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Bitki görseli
            Card(
                shape = RoundedCornerShape(100.dp),
                modifier = Modifier.size(200.dp)
            ) {
                Image(
                    painter = painterResource(id = plantRes),
                    contentDescription = "Bitki Durumu",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Puan göstergesi
            Text("Bitki Puanın: $plantScore", style = MaterialTheme.typography.titleMedium)
            LinearProgressIndicator(
                progress = safeScore / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
