package com.example.todoapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.viewmodels.TodoViewModel

// UI för att hantera Todo-listan
@Composable
fun TodoListUI(viewModel: TodoViewModel) {
    // Hanterar text för ny uppgift
    var newTask by remember { mutableStateOf("") }

    // Hämtar uppgiftslistan från ViewModel med hjälp av StateFlow
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Titel för Todo-listan
        Text(
            text = "Mina Aktiviteter",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sektion för att lägga till nya uppgifter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Textfält för inmatning av ny uppgift
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
                        // Platshållartext om textfältet är tomt
                        if (newTask.isEmpty()) {
                            Text(text = "Lägg till en aktivitet")
                        }
                        innerTextField()
                    }
                }
            )

            // Knapp för att lägga till en ny uppgift
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    viewModel.addTask(newTask) // Lägger till uppgiften i databasen
                    newTask = "" // Återställer textfältet
                }
            }) {
                Text("Lägg till")
            }
        }

        // Visar befintliga uppgifter från listan
        tasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Visar uppgiftens namn
                Text(task.taskName, modifier = Modifier.weight(1f))

                // Knapp för att ta bort en uppgift
                Button(onClick = { viewModel.removeTask(task) }) {
                    Text("Ta bort")
                }
            }
        }
    }
}
