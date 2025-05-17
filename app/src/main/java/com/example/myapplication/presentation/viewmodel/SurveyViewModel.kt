package com.example.myapplication.presentation.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.surveyQuestions

class SurveyViewModel : ViewModel() {

    // Her sorunun cevabını burada tut
    val answers = mutableStateMapOf<Int, List<String>>() // multi/single fark etmiyor, hep liste

    // Cevap ekleme
    fun saveAnswer(questionId: Int, selected: List<String>) {
        answers[questionId] = selected
    }

    // Toplam puanı hesapla
    fun calculateScore(): Int {
        var score = 0

        for ((id, selected) in answers) {
            val q = surveyQuestions.find { it.id == id } ?: continue

            selected.forEach {
                score += when (it.lowercase()) {
                    "led ampul" -> 10
                    "tasarruflu beyaz eşyalar" -> 10
                    "gereksiz ışıkları kapatma" -> 10
                    "toplu taşıma" -> 10
                    "bisiklet / scooter" -> 10
                    "evet, düzenli" -> 10
                    "kağıt / karton", "plastik", "cam", "metal" -> 5
                    "5 dakikadan az" -> 10
                    "hayır, dikkat ederim" -> 10
                    "evet" -> 5
                    else -> 0
                }
            }
        }

        return score
    }
}
