package com.fiap.todolist.viewmodel

import com.fiap.todolist.model.Task
import com.fiap.todolist.model.TaskSort

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val selectedSort: TaskSort = TaskSort.CREATED_AT,
    val taskBeingEdited: Task? = null,
    val isTaskEditorOpen: Boolean = false
)
