package com.example.todoapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.todoapp.viewmodels.TodoViewModel

// Detaljsida med kategoriserad visning av aktiviteter
@Composable
fun DetailsFragment(navController: NavHostController, viewModel: TodoViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList()) // Hämtar uppgifter

    // Dela upp uppgifter i kategorier
    val withSubTasks = tasks.filter { it.subTasks.isNotEmpty() }
    val withoutSubTasks = tasks.filter { it.subTasks.isEmpty() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Detaljer om dina aktiviteter:", modifier = Modifier.padding(bottom = 16.dp))

        // Visa uppgifter med deluppgifter
        if (withSubTasks.isNotEmpty()) {
            Text(text = "Aktiviteter med deluppgifter:", modifier = Modifier.padding(bottom = 8.dp))
            withSubTasks.forEach { task ->
                Text(text = "- ${task.taskName}")
                task.subTasks.forEach { subTask ->
                    Text(text = "  • ${subTask.name}", modifier = Modifier.padding(start = 16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Visa uppgifter utan deluppgifter
        if (withoutSubTasks.isNotEmpty()) {
            Text(text = "Aktiviteter utan deluppgifter:", modifier = Modifier.padding(bottom = 8.dp))
            withoutSubTasks.forEach { task ->
                Text(text = "- ${task.taskName}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigeringsknappar
        Button(onClick = { navController.navigate("homeFragment") }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Gå tillbaka till Hem")
        }

        Button(onClick = { navController.navigate("settingsFragment") }, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Inställningar")
        }
    }
}
