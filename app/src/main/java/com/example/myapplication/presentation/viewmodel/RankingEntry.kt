package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Bir sıralama satırı için model
data class RankingEntry(val name: String, val score: Int)

class RankingsViewModel : ViewModel() {

    // ➊ Mevcut kapsamı tutan StateFlow
    private val _scope = MutableStateFlow(Scope.NEIGHBORHOOD)
    val scope: StateFlow<Scope> = _scope.asStateFlow()

    // ➋ Örnek veriler
    private val neighborhoodData = listOf(
        RankingEntry("Ayşe", 95),
        RankingEntry("Sen (Senin Adın)", 90),
        RankingEntry("Mehmet", 85),
        RankingEntry("Fatma", 80)
    )
    private val cityData = listOf(
        RankingEntry("Ali", 200),
        RankingEntry("Veli", 195),
        RankingEntry("Sen (Senin Adın)", 190),
        RankingEntry("Ayşe", 185)
    )
    private val countryData = listOf(
        RankingEntry("ÜlkeBirincisi", 1000),
        RankingEntry("Ülkeİkincisi", 950),
        RankingEntry("Sen (Senin Adın)", 900),
        RankingEntry("Başka", 850)
    )

    // ➌ scope değiştikçe uygun listeyi seç, ViewModelScope içinde StateFlow’a dönüştür
    val currentList: StateFlow<List<RankingEntry>> = _scope
        .map { s ->
            when (s) {
                Scope.NEIGHBORHOOD -> neighborhoodData
                Scope.CITY         -> cityData
                Scope.COUNTRY      -> countryData
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = neighborhoodData
        )

    // ➍ Dışarıdan kapsamı değiştiren fonksiyon
    fun setScope(newScope: Scope) {
        _scope.value = newScope
    }

    // Kapsam seçenekleri
    enum class Scope { NEIGHBORHOOD, CITY, COUNTRY }
}
