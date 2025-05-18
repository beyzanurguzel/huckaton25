package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.presentation.viewmodel.BillPredictionViewModel
import java.text.NumberFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillPredictionScreen(
    navController: NavController,
    viewModel: BillPredictionViewModel = viewModel()
) {
    val totalElec by viewModel.electricity.collectAsState()
    val totalWater by viewModel.water.collectAsState()

    // Hesaplamalar
    val sumElec = viewModel.totalElectricity()
    val sumWater = viewModel.totalWater()
    val billElec = viewModel.predictedElectricityBill()
    val billWater = viewModel.predictedWaterBill()
    val currency = NumberFormat.getCurrencyInstance(Locale("tr","TR"))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Fatura Tahmini") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1) Özet Kartları
            SummaryCard(
                title = "Elektrik Tüketimi (30 gün)",
                value = String.format("%.1f kWh", sumElec),
                sub = "Tahmini Fatura: ${currency.format(billElec)}",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
            SummaryCard(
                title = "Su Tüketimi (30 gün)",
                value = String.format("%.1f m³", sumWater),
                sub = "Tahmini Fatura: ${currency.format(billWater)}",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )

            // 2) Detay Butonu
            Button(
                onClick = { /* İleriye grafik ya da geçmişe detay akışı */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Detaylı İncele")
            }
        }
    }
}
