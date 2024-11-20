package com.example.todoapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodels.TodoViewModel

// Huvudaktiviteten för appen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aktiverar visningen från kant till kant
        enableEdgeToEdge()

        // Här sätts appens UI
        setContent {
            TodoAppTheme {
                // Scaffold hanterar gränssnittets layout
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        TodoListScreen() // Anropar TodoList-skärmen
                    }
                }
            }
        }
    }
}

// Skärm som visar Todo-listans UI
@Composable
fun TodoListScreen(viewModel: TodoViewModel = viewModel()) {
    TodoListUI(viewModel = viewModel) // Kopplar till TodoListUI
}
