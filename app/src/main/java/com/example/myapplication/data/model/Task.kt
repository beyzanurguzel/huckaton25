package com.example.myapplication.data.model

data class Task(
    val id: Long,
    val title: String,
    val isDone: Boolean = false
)
