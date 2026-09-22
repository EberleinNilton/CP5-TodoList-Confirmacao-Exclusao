package com.fiap.todolist.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fiap.todolist.model.Task
import com.fiap.todolist.ui.theme.CP5TodoListTheme

@Composable
fun DeleteConfirmationDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Excluir tarefa?")
        },
        text = {
            Text(
                text = "A tarefa \"${task.title}\" será excluída definitivamente. Deseja continuar?"
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Excluir",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Preview(
    name = "Confirmação de exclusão",
    showBackground = true,
    widthDp = 380,
    heightDp = 700
)
@Composable
private fun DeleteConfirmationDialogPreview() {
    CP5TodoListTheme(dynamicColor = false) {
        DeleteConfirmationDialog(
            task = Task(
                id = 1L,
                title = "Finalizar atividade de Android",
                description = "Implementar confirmação antes de excluir"
            ),
            onDismiss = {},
            onConfirm = {}
        )
    }
}
