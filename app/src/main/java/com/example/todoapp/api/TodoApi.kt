package com.example.todoapp.api

import com.example.todoapp.database.TaskDatabaseOperations
import com.example.todoapp.database.TodoEntity
import kotlinx.coroutines.flow.firstOrNull

// API som hanterar kommunikation mellan databasen och applikationen
class TodoApi(private val dao: TaskDatabaseOperations) {

    // Hämtar alla uppgifter från databasen och konverterar till TaskWithSubTasks
    suspend fun getTasks(): List<TaskWithSubTasks> {
        val tasks = dao.getAllTasks().firstOrNull() // Hämtar första listan från Flow
        return tasks?.map { entity ->
            TaskWithSubTasks(
                id = entity.id,
                taskName = entity.taskName,
                completed = false, // Standardvärde för "completed"
                subTasks = entity.subTasks // Kopplade deluppgifter
            )
        } ?: emptyList() // Returnerar tom lista om inget finns
    }

    // Lägger till en ny uppgift
    suspend fun addTask(task: TaskWithSubTasks) {
        dao.insertTask(
            TodoEntity(
                id = task.id, // ID för uppgiften
                taskName = task.taskName, // Namn på uppgiften
                subTasks = task.subTasks // Lista av deluppgifter
            )
        )
    }

    // Uppdaterar en befintlig uppgift
    suspend fun updateTask(task: TaskWithSubTasks) {
        dao.updateTask(
            TodoEntity(
                id = task.id,
                taskName = task.taskName,
                subTasks = task.subTasks
            )
        )
    }

    // Tar bort en specifik uppgift
    suspend fun deleteTask(taskId: Int) {
        val tasks = dao.getAllTasks().firstOrNull() // Hämtar aktuell lista
        val entityToDelete = tasks?.find { it.id == taskId } // Hittar uppgiften via ID
        entityToDelete?.let { dao.deleteTask(it) }
    }

    // Tömmer alla aktiviteter
    suspend fun clearAllTasks() {
        dao.clearAllTasks() // Använder DAO för att rensa databasen
    }
}
