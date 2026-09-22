package com.fiap.todolist.viewmodel

import androidx.lifecycle.ViewModel
import com.fiap.todolist.data.TaskRepository
import com.fiap.todolist.model.Task
import com.fiap.todolist.model.TaskSort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class TaskViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TaskUiState(tasks = repository.getTasks())
    )
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    fun openNewTaskEditor() {
        _uiState.value = _uiState.value.copy(
            isTaskEditorOpen = true,
            taskBeingEdited = null
        )
    }

    fun openEditTaskEditor(task: Task) {
        _uiState.value = _uiState.value.copy(
            isTaskEditorOpen = true,
            taskBeingEdited = task
        )
    }

    fun closeTaskEditor() {
        _uiState.value = _uiState.value.copy(
            isTaskEditorOpen = false,
            taskBeingEdited = null
        )
    }

    fun saveTask(
        title: String,
        description: String,
        dueDate: LocalDate?
    ) {
        val normalizedTitle = title.trim()
        if (normalizedTitle.isBlank()) return

        val editing = _uiState.value.taskBeingEdited
        if (editing == null) {
            repository.addTask(
                title = normalizedTitle,
                description = description.trim(),
                dueDate = dueDate
            )
        } else {
            repository.updateTask(
                editing.copy(
                    title = normalizedTitle,
                    description = description.trim(),
                    dueDate = dueDate
                )
            )
        }

        refreshTasks()
        closeTaskEditor()
    }

    fun toggleCompleted(taskId: Long) {
        repository.toggleCompleted(taskId)
        refreshTasks()
    }

    fun deleteTask(taskId: Long) {
        repository.deleteTask(taskId)
        refreshTasks()
    }

    fun changeSort(sort: TaskSort) {
        _uiState.value = _uiState.value.copy(selectedSort = sort)
        refreshTasks()
    }

    private fun refreshTasks() {
        val sort = _uiState.value.selectedSort
        val sorted = when (sort) {
            TaskSort.CREATED_AT -> repository.getTasks().sortedByDescending { it.createdAt }
            TaskSort.DUE_DATE -> repository.getTasks().sortedWith(
                compareBy<Task> { it.dueDate == null }.thenBy { it.dueDate }
            )
            TaskSort.TITLE -> repository.getTasks().sortedBy { it.title.lowercase() }
            TaskSort.STATUS -> repository.getTasks().sortedWith(
                compareBy<Task> { it.isCompleted }.thenByDescending { it.createdAt }
            )
        }
        _uiState.value = _uiState.value.copy(tasks = sorted)
    }
}
