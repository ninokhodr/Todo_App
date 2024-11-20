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
import com.example.todoapp.viewmodels.TodoViewModel

// UI för att hantera Todo-listan
@Composable
fun TodoListUI(viewModel: TodoViewModel) {
    // Hanterar en ny uppgift
    var newTask by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Titel
        Text(
            text = "Mina Aktiviteter",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Inmatning och knapp för att lägga till en uppgift
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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

            Button(onClick = {
                if (newTask.isNotBlank()) {
                    viewModel.addTask(newTask)
                    newTask = ""
                }
            }) {
                Text("Lägg till")
            }
        }

        // Visar befintliga uppgifter
        viewModel.tasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(task, modifier = Modifier.weight(1f))
                Button(onClick = { viewModel.removeTask(task) }) {
                    Text("Ta bort")
                }
            }
        }
    }
}
