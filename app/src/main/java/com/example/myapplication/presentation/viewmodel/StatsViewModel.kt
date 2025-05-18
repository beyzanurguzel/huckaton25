// app/src/main/java/com/example/myapplication/presentation/viewmodel/StatsViewModel.kt
package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.DataPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {
    // TODO: Bunları gerçek sayaç API’nle değiştir
    private val _electricity = MutableStateFlow<List<DataPoint>>(emptyList())
    val electricity: StateFlow<List<DataPoint>> = _electricity

    private val _water = MutableStateFlow<List<DataPoint>>(emptyList())
    val water: StateFlow<List<DataPoint>> = _water

    init {
        // Demo veri üretimi: her saniye rastgele değer ekle
        viewModelScope.launch {
            val start = System.currentTimeMillis()
            while(true) {
                val now = System.currentTimeMillis()
                val elapsed = ((now - start) / 1000f).coerceAtMost(60f)
                _electricity.value = _electricity.value + DataPoint(elapsed.toLong(), (5..15).random().toFloat())
                _water.value      = _water.value      + DataPoint(elapsed.toLong(), (2..8).random().toFloat())
                delay(1_000)
            }
        }
    }
}
