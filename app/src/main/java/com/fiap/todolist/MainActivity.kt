package com.fiap.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fiap.todolist.ui.screens.TaskListRoute
import com.fiap.todolist.ui.theme.CP5TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CP5TodoListTheme {
                TaskListRoute()
            }
        }
    }
}
