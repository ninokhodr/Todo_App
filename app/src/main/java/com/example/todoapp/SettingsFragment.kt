package com.example.todoapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todoapp.viewmodels.TodoViewModel

// Inställningsskärmen
@Composable
fun SettingsFragment(navController: NavHostController, viewModel: TodoViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Inställningar:", modifier = Modifier.padding(bottom = 16.dp))

        // Knapp för att tömma alla aktiviteter
        Button(
            onClick = { viewModel.clearAllTasks() }, // Rensar databasen
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Töm alla aktiviteter")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigeringsknappar
        Button(onClick = { navController.navigate("homeFragment") }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Gå tillbaka till Hem")
        }
        Button(onClick = { navController.navigate("detailsFragment") }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Detaljer")
        }
    }
}
