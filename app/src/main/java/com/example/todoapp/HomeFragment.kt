package com.example.todoapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todoapp.viewmodels.TodoViewModel

// Huvudskärmen för Todo-appen
@Composable
fun HomeFragment(navController: NavHostController, viewModel: TodoViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Visar TodoListUI som huvudskärmen
        Box(
            modifier = Modifier
                .weight(1f) // Fyller upp så att navigeringsknapparna hamnar längst ned
        ) {
            TodoListUI(viewModel = viewModel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigeringsknappar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.navigate("detailsFragment") }) {
                Text(text = "Detaljer")
            }
            Button(onClick = { navController.navigate("settingsFragment") }) {
                Text(text = "Inställningar")
            }
        }
    }
}
