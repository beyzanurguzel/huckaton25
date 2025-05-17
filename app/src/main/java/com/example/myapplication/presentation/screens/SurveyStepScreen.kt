package com.example.myapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.surveyQuestions
import com.example.myapplication.presentation.viewmodel.SurveyViewModel

@Composable
fun SurveyStepScreen(
    navController: NavController,
    questionId: Int,
    viewModel: SurveyViewModel
) {
    val question = surveyQuestions.getOrNull(questionId - 1)
    if (question == null) {
        navController.navigate("survey_result")
        return
    }

    val isMulti = question.type == "multi"
    val selectedSingle = remember { mutableStateOf("") }
    val selectedMulti = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Başlık ve ilerleme
        Text(
            text = "Soru $questionId / ${surveyQuestions.size}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Start),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = questionId / surveyQuestions.size.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column {
            question.options.forEach { option ->
                val isSelected = if (isMulti) option in selectedMulti else selectedSingle.value == option

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable {
                            if (isMulti) {
                                if (option in selectedMulti) selectedMulti.remove(option)
                                else selectedMulti.add(option)
                            } else {
                                selectedSingle.value = option
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (isMulti) {
                    viewModel.saveAnswer(questionId, selectedMulti.toList())
                } else {
                    viewModel.saveAnswer(questionId, listOf(selectedSingle.value))
                }

                val nextId = questionId + 1
                if (nextId <= surveyQuestions.size) {
                    navController.navigate("survey_step/$nextId")
                } else {
                    navController.navigate("survey_result")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = isMulti && selectedMulti.isNotEmpty() || !isMulti && selectedSingle.value.isNotEmpty()
        ) {
            Text("İleri", style = MaterialTheme.typography.titleMedium)
        }
    }
}
