package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.surveyQuestions // soruların geldiği yer

@Composable
fun SurveyStepScreen(
    navController: NavController,
    questionId: Int
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
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Soru $questionId", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = question.question, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEach { option ->
            if (isMulti) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .toggleable(
                            value = option in selectedMulti,
                            onValueChange = {
                                if (it) selectedMulti.add(option) else selectedMulti.remove(option)
                            }
                        )
                ) {
                    Checkbox(
                        checked = option in selectedMulti,
                        onCheckedChange = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option)
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedSingle.value == option,
                        onClick = { selectedSingle.value = option }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val nextId = questionId + 1
                if (nextId <= surveyQuestions.size) {
                    navController.navigate("survey_step/$nextId")
                } else {
                    navController.navigate("survey_result")
                }
            },
            enabled = isMulti && selectedMulti.isNotEmpty() || !isMulti && selectedSingle.value.isNotEmpty()
        ) {
            Text("İleri")
        }
    }
}
