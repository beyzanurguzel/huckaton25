package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication.data.model.Task

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private var nextId = 1L

    fun addTask(title: String) {
        if (title.isBlank()) return
        val new = Task(id = nextId++, title = title.trim())
        _tasks.value = _tasks.value + new
    }

    fun toggleDone(id: Long) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(isDone = !it.isDone) else it
        }
    }

    fun removeTask(id: Long) {
        _tasks.value = _tasks.value.filterNot { it.id == id }
    }
}
