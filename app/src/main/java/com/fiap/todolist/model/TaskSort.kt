package com.fiap.todolist.model

enum class TaskSort(val label: String) {
    CREATED_AT("Mais recentes"),
    DUE_DATE("Prazo"),
    TITLE("Título"),
    STATUS("Status")
}
