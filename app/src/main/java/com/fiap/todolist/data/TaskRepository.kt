package com.fiap.todolist.data

import com.fiap.todolist.model.Task

class TaskRepository {
    private var nextId = 4L

    private val tasks = mutableListOf(
        Task(
            id = 1L,
            title = "Finalizar atividade de Android",
            description = "Implementar confirmação antes de excluir uma tarefa.",
            dueDate = java.time.LocalDate.now().plusDays(1)
        ),
        Task(
            id = 2L,
            title = "Revisar conteúdo de Kotlin",
            description = "Revisar ViewModel, estado e Jetpack Compose.",
            dueDate = java.time.LocalDate.now().minusDays(1)
        ),
        Task(
            id = 3L,
            title = "Enviar trabalho na FIAP",
            description = "Conferir evidências e link do GitHub.",
            dueDate = java.time.LocalDate.now().plusDays(3),
            isCompleted = true
        )
    )

    fun getTasks(): List<Task> = tasks.toList()

    fun addTask(title: String, description: String, dueDate: java.time.LocalDate?): Task {
        val task = Task(
            id = nextId++,
            title = title,
            description = description,
            dueDate = dueDate
        )
        tasks.add(task)
        return task
    }

    fun updateTask(updatedTask: Task) {
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index >= 0) {
            tasks[index] = updatedTask
        }
    }

    fun deleteTask(taskId: Long) {
        tasks.removeAll { it.id == taskId }
    }

    fun toggleCompleted(taskId: Long) {
        val index = tasks.indexOfFirst { it.id == taskId }
        if (index >= 0) {
            val current = tasks[index]
            tasks[index] = current.copy(isCompleted = !current.isCompleted)
        }
    }
}
