package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DataPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BillPredictionViewModel : ViewModel() {
    // Gerçek zamanlı sayaç verisi Flow’ları (StatsViewModel’dan da çekilebilir)
    private val _electricity = MutableStateFlow<List<DataPoint>>(emptyList())
    val electricity: StateFlow<List<DataPoint>> = _electricity

    private val _water = MutableStateFlow<List<DataPoint>>(emptyList())
    val water: StateFlow<List<DataPoint>> = _water

    // Birim fiyatlar (örn. kullanıcı ayarından çekilebilir)
    var pricePerKwh: Float = 2.0f   // TL/kWh
    var pricePerM3: Float = 15.0f   // TL/m³

    init {
        // Demo amaçlı rastgele veya sabit geçmiş verileri yükle
        viewModelScope.launch {
            // Örneğin son 30 günlük snapshot
            val now = System.currentTimeMillis()
            _electricity.value = List(30) { i ->
                DataPoint(timestamp = i.toLong(), value = (5..15).random().toFloat())
            }
            _water.value = List(30) { i ->
                DataPoint(timestamp = i.toLong(), value = (1..5).random().toFloat())
            }
        }
    }

    // Son 30 günlük toplam tüketim
    fun totalElectricity(): Float = electricity.value.sumOf { it.value.toDouble() }.toFloat()
    fun totalWater(): Float = water.value.sumOf { it.value.toDouble() }.toFloat()

    // Tahmini fatura
    fun predictedElectricityBill(): Float = totalElectricity() * pricePerKwh
    fun predictedWaterBill(): Float = totalWater() * pricePerM3
}
