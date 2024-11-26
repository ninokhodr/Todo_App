package com.example.todoapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskDatabaseOperations
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.database.TodoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel som hanterar logiken och datan för to do-listan
class TodoViewModel(application: Application) : AndroidViewModel(application) {

    // Ger åtkomst till databasen genom DAO (Data Access Object)
    private val taskDao: TaskDatabaseOperations =
        TodoDatabase.getInstance(application).taskDatabaseOperations()

    // MutableStateFlow som används för att hålla och observera listan av uppgifter
    private val _tasks = MutableStateFlow<List<TodoEntity>>(emptyList())
    val tasks: StateFlow<List<TodoEntity>> get() = _tasks

    // Initierar ViewModel och laddar uppgifter från databasen
    init {
        loadTasks()
    }

    // Hämtar alla uppgifter från databasen och uppdaterar _tasks
    private fun loadTasks() {
        viewModelScope.launch {
            taskDao.getAllTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    // Lägger till en ny uppgift i databasen
    fun addTask(taskName: String) {
        if (taskName.isNotBlank()) {
            val newTask = TodoEntity(taskName = taskName)
            viewModelScope.launch {
                taskDao.insertTask(newTask) // Lägger till uppgiften i databasen
                loadTasks() // Uppdaterar listan efter att uppgiften har lagts till
            }
        }
    }

    // Tar bort en uppgift från databasen
    fun removeTask(task: TodoEntity) {
        viewModelScope.launch {
            taskDao.deleteTask(task) // Tar bort uppgiften från databasen
            loadTasks() // Uppdaterar listan efter att uppgiften har tagits bort
        }
    }
}
