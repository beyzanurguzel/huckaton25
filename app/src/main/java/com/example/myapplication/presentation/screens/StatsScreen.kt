package com.example.myapplication.presentation.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.data.model.DataPoint
import com.example.myapplication.presentation.viewmodel.StatsViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.ui.Alignment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import  com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: StatsViewModel = viewModel()
) {
    // collectAsState için doğru getValue import’u ve destructuring
    val electricity by viewModel.electricity.collectAsState()
    val water by viewModel.water.collectAsState()

    // Özet hesaplamaları
    val totalElec = electricity.sumOf { dp -> dp.value.toDouble() }.toFloat()
    val totalWater = water.sumOf { dp -> dp.value.toDouble() }.toFloat()
    val avgElec = if (electricity.isNotEmpty()) totalElec / electricity.size else 0f
    val avgWater = if (water.isNotEmpty()) totalWater / water.size else 0f

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("İstatistikler") },
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
            // 1️⃣ Özet Kartları
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = "Elektrik",
                    value = "%.1f kWh".format(totalElec),
                    sub = "Günlük ort: %.1f".format(avgElec),
                    color = Color(0xFF2979FF)
                )
                SummaryCard(
                    title = "Su",
                    value = "%.1f m³".format(totalWater),
                    sub = "Günlük ort: %.1f".format(avgWater),
                    color = Color(0xFF00BFA5)
                )
            }

            // 2️⃣ Elektrik Grafiği
            Text("Elektrik Tüketimi", style = MaterialTheme.typography.titleMedium)
            LineChartView(data = electricity, lineColor = Color(0xFF2979FF).toArgb())

            // 3️⃣ Su Grafiği
            Text("Su Tüketimi", style = MaterialTheme.typography.titleMedium)
            LineChartView(data = water, lineColor = Color(0xFF00BFA5).toArgb())

            // 4️⃣ Ay Bazlı Karşılaştırma
            Text("Ay Bazlı Karşılaştırma", style = MaterialTheme.typography.titleMedium)
            BarComparisonChart(
                current = totalElec,
                previous = 100f,
                label = "kWh"
            )
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    sub: String,
    color: Color,
    modifier: Modifier = Modifier  // ➊
) {
    Card(
        modifier = modifier            // ➋
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = color)
            Column {
                Text(
                    value,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = color
                )
                Text(sub, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
private fun LineChartView(
    data: List<DataPoint>,
    lineColor: Int
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { ctx ->
            LineChart(ctx).apply {
                // — Viewport ve offset ayarları —
                setViewPortOffsets(16f, 16f, 16f, 16f)
                setExtraOffsets(16f, 0f, 0f, 16f)

                // — Genel temizleme —
                description.isEnabled = false
                legend.isEnabled = false
                setNoDataText("")      // veri yok mesajını kaldır
                setDrawGridBackground(false)

                // — X ekseni ayarları —
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    setDrawLabels(true)
                    granularity = 1f
                    labelCount = data.size.coerceAtMost(7)
                    valueFormatter = IndexAxisValueFormatter(
                        data.mapIndexed { index, _ -> index.toString() }
                    )
                }

                // — Y ekseni (soldaki) ayarları —
                axisLeft.apply {
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    axisMinimum = 0f
                }
                axisRight.isEnabled = false
            }
        },
        update = { chart ->
            // Her güncelleme öncesi temizle
            chart.clear()

            val entries = data.mapIndexed { index, dp ->
                Entry(index.toFloat(), dp.value)
            }
            val set = LineDataSet(entries, "").apply {
                color = lineColor
                setDrawCircles(true)
                circleRadius = 4f
                setCircleColor(lineColor)
                lineWidth = 2f
                valueTextSize = 10f
            }

            chart.data = LineData(set)
            chart.invalidate()
        }
    )
}



@Composable
fun BarComparisonChart(
    current: Float,
    previous: Float,
    label: String
) {
    // Compose kapsamında renkleri hazırlıyoruz
    val primaryColor = MaterialTheme.colorScheme.primary.toArgb()
    val grayColor = Color.LightGray.toArgb()

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { ctx ->
            BarChart(ctx).apply {
                // ➊ Kenar boşlukları ve viewport
                setViewPortOffsets(16f, 16f, 16f, 16f)
                setExtraOffsets(16f, 0f, 0f, 16f)

                // ➋ Genel temizleme
                description.isEnabled = false
                legend.isEnabled = false
                setDrawGridBackground(false)
                setNoDataText("")

                // ➌ X ekseni ayarları
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                    granularity = 1f
                    labelCount = 2
                    valueFormatter = IndexAxisValueFormatter(listOf("Geçen ay", "Bu ay"))
                }

                // ➍ Y ekseni ayarları
                axisLeft.apply {
                    axisMinimum = 0f
                    setDrawLabels(false)
                    setDrawGridLines(false)
                    setDrawAxisLine(false)
                }

                axisRight.isEnabled = false
            }
        },
        update = { chart ->
            // ➎ Her update başında temizle
            chart.clear()

            // BarEntry’ler
            val entries = listOf(
                BarEntry(0f, previous),
                BarEntry(1f, current)
            )
            val set = BarDataSet(entries, "").apply {
                setColors(grayColor, primaryColor)
                valueTextColor = Color.Black.toArgb()
                valueTextSize = 12f
            }

            chart.data = BarData(set).apply {
                barWidth = 0.5f
            }
            chart.invalidate()
        }
    )
}



