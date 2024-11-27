package com.example.todoapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.api.SubTask
import com.example.todoapp.api.TaskWithSubTasks
import com.example.todoapp.viewmodels.TodoViewModel

@Composable
fun TodoListUI(viewModel: TodoViewModel) {
    var newTask by remember { mutableStateOf("") } // Ny huvuduppgift
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(initialValue = emptyList()) // Hämta uppgifter från ViewModel
    val subTasks = remember { mutableStateListOf<SubTask>() } // Tillfällig lista för deluppgifter
    var taskToEdit by remember { mutableStateOf<TaskWithSubTasks?>(null) } // Uppgift som redigeras
    var showTasks by remember { mutableStateOf(true) } // Växla visa/dölj för uppgifter

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Gör layouten skrollbar
    ) {
        // Rubrik
        Text(
            text = "Mina Aktiviteter",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lägg till ny huvuduppgift
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Textfält för ny uppgift
            BasicTextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(56.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (newTask.isEmpty()) {
                            Text(text = "Lägg till en aktivitet")
                        }
                        innerTextField()
                    }
                }
            )
            // Knapp för att lägga till ny uppgift
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    viewModel.addTask(newTask, subTasks.toList()) // Lägg till via ViewModel
                    newTask = "" // Återställ textfältet
                    subTasks.clear() // Rensa tillfälliga deluppgifter
                }
            }) {
                Text("Lägg till")
            }
        }

        // Visa uppgifter om toggle är aktiv
        if (showTasks) {
            tasks.forEach { task ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    // Visa huvuduppgift
                    Text(
                        "Uppgift: ${task.taskName}",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                    )
                    // Visa deluppgifter
                    task.subTasks.forEach { subTask ->
                        Text("- ${subTask.name}")
                    }
                    // Knappar för att ta bort eller redigera uppgiften
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { viewModel.removeTask(task.id) }) {
                            Text("Ta bort uppgift")
                        }
                        Button(onClick = { taskToEdit = task }) {
                            Text("Redigera")
                        }
                    }
                }
            }
        }

        // Dialogruta för redigering av uppgift
        taskToEdit?.let { task ->
            var editedTaskName by remember { mutableStateOf(task.taskName) } // Redigerbart namn
            val editedSubTasks = remember { mutableStateListOf(*task.subTasks.toTypedArray()) } // Redigerbar lista av deluppgifter
            var newSubTask by remember { mutableStateOf("") } // Ny deluppgift

            AlertDialog(
                onDismissRequest = { taskToEdit = null }, // Stäng dialogen
                title = { Text("Redigera Uppgift") },
                text = {
                    Column {
                        // Redigera huvuduppgiftens namn
                        BasicTextField(
                            value = editedTaskName,
                            onValueChange = { editedTaskName = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        // Visa och redigera deluppgifter
                        editedSubTasks.forEachIndexed { index, subTask ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Textfält för deluppgift
                                BasicTextField(
                                    value = subTask.name,
                                    onValueChange = { newName ->
                                        editedSubTasks[index] = subTask.copy(name = newName)
                                    },
                                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                                )
                                // Ta bort deluppgift
                                Button(onClick = { editedSubTasks.removeAt(index) }) {
                                    Text("Ta bort")
                                }
                            }
                        }
                        // Lägg till ny deluppgift
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            BasicTextField(
                                value = newSubTask,
                                onValueChange = { newSubTask = it },
                                modifier = Modifier.weight(1f).padding(end = 8.dp)
                            )
                            Button(onClick = {
                                if (newSubTask.isNotBlank()) {
                                    editedSubTasks.add(SubTask(id = 0, name = newSubTask, completed = false))
                                    newSubTask = "" // Återställ fältet
                                }
                            }) {
                                Text("Lägg till deluppgift")
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val updatedTask = task.copy(
                            taskName = editedTaskName,
                            subTasks = editedSubTasks.toList()
                        )
                        viewModel.updateTask(updatedTask) // Uppdatera uppgiften
                        taskToEdit = null
                    }) {
                        Text("Spara")
                    }
                },
                dismissButton = {
                    Button(onClick = { taskToEdit = null }) {
                        Text("Avbryt")
                    }
                }
            )
        }

        // Toggle-knapp
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { showTasks = !showTasks }, // Växlar läge mellan att visa och dölja uppgifter
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(if (showTasks) "Dölj Uppgifter" else "Visa Uppgifter")
        }
    }
}
