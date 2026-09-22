package com.fiap.todolist.model

import java.time.LocalDate

data class Task(
    val id: Long,
    val title: String,
    val description: String = "",
    val dueDate: LocalDate? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
