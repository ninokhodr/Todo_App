package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodels.TodoViewModel

// Huvudaktivitet för Todo-appen
class MainActivity : ComponentActivity() {
    private val viewModel: TodoViewModel by viewModels() // Skapar en instans av ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                TodoListUI(viewModel = viewModel)
            }
        }
    }
}
