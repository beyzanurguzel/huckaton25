package com.example.myapplication.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.surveyQuestions
import com.example.myapplication.presentation.viewmodel.SurveyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyStepScreen(
    navController: NavController,
    questionId: Int,
    viewModel: SurveyViewModel
) {
    val question = surveyQuestions.getOrNull(questionId - 1) ?: run {
        navController.navigate("survey_result")
        return
    }
    val total = surveyQuestions.size
    val progress = questionId / total.toFloat()

    val isMulti = question.type == "multi"
    val selectedSingle = remember { mutableStateOf("") }
    val selectedMulti = remember { mutableStateListOf<String>() }

    val gradientColors = listOf(Color(0xFF66BB6A), Color(0xFF43A047))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        // Progress indicator with step count
        Text(
            text = "Soru $questionId / $total",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Question card
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(gradientColors),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Options
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            question.options.forEach { option ->
                val isSelected = if (isMulti) selectedMulti.contains(option) else selectedSingle.value == option
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isMulti) {
                                if (isSelected) selectedMulti.remove(option)
                                else selectedMulti.add(option)
                            } else {
                                selectedSingle.value = option
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surface
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSelected) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.checkbox_on_background),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Cevabı kaydet (burada calculateScore() devreye girer)
                if (isMulti) viewModel.saveAnswer(questionId, selectedMulti.toList())
                else viewModel.saveAnswer(questionId, listOf(selectedSingle.value))

                // Son soruya mı geldik?
                if (questionId < total) {
                    navController.navigate("survey_step/${questionId + 1}")
                } else {
                    // Anket bitti → puan zaten güncellenmiş, şimdi ana sayfaya yönlen
                    navController.navigate("home") {
                        popUpTo("selection") { inclusive = true }
                    }
                }
            },
            /* … */
        ) { Text("İleri") }



    }
}
