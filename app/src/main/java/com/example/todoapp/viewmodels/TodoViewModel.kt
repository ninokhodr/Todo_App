package com.example.todoapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.api.TaskWithSubTasks
import com.example.todoapp.api.SubTask
import com.example.todoapp.api.TodoApi
import com.example.todoapp.database.TodoDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel för att hantera uppgifter och deluppgifter
class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val api = TodoApi(TodoDatabase.getInstance(application).taskDatabaseOperations())
    private val _tasks = MutableStateFlow<List<TaskWithSubTasks>>(emptyList())
    val tasks: StateFlow<List<TaskWithSubTasks>> get() = _tasks

    init {
        fetchTasks() // Hämtar uppgifter vid start
    }

    // Hämtar alla uppgifter från databasen
    fun fetchTasks() {
        viewModelScope.launch {
            _tasks.value = api.getTasks()
        }
    }

    // Lägger till en ny uppgift
    fun addTask(taskName: String, subTasks: List<SubTask>) {
        if (taskName.isNotBlank()) {
            val newTask = TaskWithSubTasks(
                id = 0,
                taskName = taskName,
                completed = false,
                subTasks = subTasks
            )
            viewModelScope.launch {
                api.addTask(newTask)
                fetchTasks()
            }
        }
    }

    // Uppdaterar en befintlig uppgift
    fun updateTask(task: TaskWithSubTasks) {
        viewModelScope.launch {
            api.updateTask(task)
            fetchTasks()
        }
    }

    // Tar bort en uppgift
    fun removeTask(taskId: Int) {
        viewModelScope.launch {
            api.deleteTask(taskId)
            fetchTasks()
        }
    }
}
