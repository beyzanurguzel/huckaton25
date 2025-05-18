package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


/**
 * Kullanıcının anket cevaplarını tutar, cevap kaydeder ve puan hesaplar.
 */
class SurveyViewModel : ViewModel() {

    // ❶ Cevapları saklayacağımız map
    private val _answers = mutableMapOf<Int, List<String>>()

    /** Cevap haritasını salt-okunur olarak dışarıya açıyoruz */
    val answers: Map<Int, List<String>>
        get() = _answers

    // ❷ Reaktif puan akışı
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    /**
     * Bu fonksiyon çağrıldığında, verilen soruya ait cevap listesini kaydeder
     * ve hemen ardından toplam puanı yeniden hesaplar.
     */
    fun saveAnswer(questionId: Int, selectedAnswers: List<String>) {
        _answers[questionId] = selectedAnswers
        calculateScore()
    }

    /**
     * _answers içindekileri puanlayıp hem StateFlow'a yazar hem de döndürür.
     *
     * @return Hesaplanan toplam puan
     */
    fun calculateScore(): Int {
        var total = 0

        for ((_, selectedList) in _answers) {
            for (answer in selectedList) {
                total += when (answer.lowercase()) {
                    // 10 puanlık iyi alışkanlıklar
                    "led ampul",
                    "tasarruflu beyaz eşyalar",
                    "gereksiz ışıkları kapatma",
                    "toplu taşıma",
                    "bisiklet / scooter",
                    "evet, düzenli",
                    "5 dakikadan az",
                    "hayır, dikkat ederim" -> 10

                    // 5 puanlık geri dönüşüm seçenekleri
                    "kağıt / karton",
                    "plastik",
                    "cam",
                    "metal" -> 5

                    // Genel “evet” cevabı
                    "evet" -> 5

                    else -> 0
                }
            }
        }

        // ❸ Hesaplanan puanı StateFlow’a yaz
        _score.value = total
        return total
    }
}

