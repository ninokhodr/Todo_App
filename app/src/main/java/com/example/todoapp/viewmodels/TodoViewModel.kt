package com.example.todoapp.viewmodels
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// ViewModel som hanterar logiken och datan för to do listan
class TodoViewModel : ViewModel() {

    // Lista där alla uppgifter sparas
    private val _tasks = mutableStateListOf<String>()
    val tasks: List<String> get() = _tasks

    // Lägger till en ny uppgift i listan
    fun addTask(task: String) {
        if (task.isNotBlank()) {
            _tasks.add(task.trim())
        }
    }

    // Tar bort en uppgift från listan
    fun removeTask(task: String) {
        _tasks.remove(task)
    }
}
