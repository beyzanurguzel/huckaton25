package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuditSuggestion(val title: String, val description: String)

class EnergyAuditViewModel : ViewModel() {
    private val _suggestions = MutableStateFlow<List<AuditSuggestion>>(emptyList())
    val suggestions: StateFlow<List<AuditSuggestion>> = _suggestions.asStateFlow()

    /**
     * Kameradan alınan verileri (şimdilik simülasyon)
     * AI servislerine gönderir, yanıtı suggestions’a yazar.
     */
    fun scanAndAnalyze() {
        viewModelScope.launch {
            // Simülasyon: gerçek AI entegrasyonu yerine 2 saniye bekle
            delay(2000)
            _suggestions.value = listOf(
                AuditSuggestion(
                    "Pencereleri İzole Et",
                    "Pencere kenarlarından giren soğuk havayı engellemek için izolasyon bandı kullanın."
                ),
                AuditSuggestion(
                    "Akıllı Termostat Kur",
                    "Evi uzaktan kontrol edip sıcaklığı optimize edebilecek bir akıllı termostat edinin."
                ),
                AuditSuggestion(
                    "LED Aydınlatmaya Geç",
                    "Tüm ampullerinizi uzun ömürlü LED’lerle değiştirerek enerji tasarrufu yapın."
                )
            )
        }
    }
}
