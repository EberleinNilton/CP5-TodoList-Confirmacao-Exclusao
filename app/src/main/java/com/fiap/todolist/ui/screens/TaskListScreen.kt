package com.fiap.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiap.todolist.model.Task
import com.fiap.todolist.model.TaskSort
import com.fiap.todolist.ui.components.TaskEditorDialog
import com.fiap.todolist.ui.components.TaskItem
import com.fiap.todolist.ui.theme.CP5TodoListTheme
import com.fiap.todolist.viewmodel.TaskUiState
import com.fiap.todolist.viewmodel.TaskViewModel
import java.time.LocalDate

@Composable
fun TaskListRoute(
    viewModel: TaskViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    TaskListScreen(
        state = state,
        onAddTask = viewModel::openNewTaskEditor,
        onEditTask = viewModel::openEditTaskEditor,
        onToggleCompleted = viewModel::toggleCompleted,
        onDeleteTask = viewModel::deleteTask,
        onDismissEditor = viewModel::closeTaskEditor,
        onSaveTask = viewModel::saveTask,
        onSortChange = viewModel::changeSort
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    state: TaskUiState,
    onAddTask: () -> Unit,
    onEditTask: (Task) -> Unit,
    onToggleCompleted: (Long) -> Unit,
    onDeleteTask: (Long) -> Unit,
    onDismissEditor: () -> Unit,
    onSaveTask: (String, String, LocalDate?) -> Unit,
    onSortChange: (TaskSort) -> Unit
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("To-Do List")
                        Text(
                            text = "${state.tasks.size} tarefa(s) • ${state.selectedSort.label}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { sortMenuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Sort,
                                contentDescription = "Ordenar tarefas"
                            )
                        }

                        DropdownMenu(
                            expanded = sortMenuExpanded,
                            onDismissRequest = { sortMenuExpanded = false }
                        ) {
                            TaskSort.entries.forEach { sort ->
                                DropdownMenuItem(
                                    text = { Text(sort.label) },
                                    onClick = {
                                        onSortChange(sort)
                                        sortMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Cadastrar tarefa"
                )
            }
        }
    ) { innerPadding ->
        if (state.tasks.isEmpty()) {
            EmptyTaskList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = state.tasks,
                    key = { it.id }
                ) { task ->
                    TaskItem(
                        task = task,
                        onToggleCompleted = { onToggleCompleted(task.id) },
                        onEdit = { onEditTask(task) },
                        onDelete = { onDeleteTask(task.id) }
                    )
                }
            }
        }
    }

    if (state.isTaskEditorOpen) {
        TaskEditorDialog(
            task = state.taskBeingEdited,
            onDismiss = onDismissEditor,
            onSave = onSaveTask
        )
    }
}

@Composable
private fun EmptyTaskList(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Nenhuma tarefa cadastrada",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Toque em + para criar a primeira tarefa.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TaskListPreview() {
    CP5TodoListTheme(dynamicColor = false) {
        TaskListScreen(
            state = TaskUiState(
                tasks = listOf(
                    Task(
                        id = 10L,
                        title = "Estudar Jetpack Compose",
                        description = "Revisar estado e Material 3",
                        dueDate = LocalDate.now().plusDays(2)
                    ),
                    Task(
                        id = 11L,
                        title = "Tarefa atrasada",
                        dueDate = LocalDate.now().minusDays(1)
                    )
                )
            ),
            onAddTask = {},
            onEditTask = {},
            onToggleCompleted = {},
            onDeleteTask = {},
            onDismissEditor = {},
            onSaveTask = { _, _, _ -> },
            onSortChange = {}
        )
    }
}
