package com.example.todoapp.api

import com.example.todoapp.database.TaskDatabaseOperations
import com.example.todoapp.database.TodoEntity
import kotlinx.coroutines.flow.firstOrNull

// API som hanterar kommunikation mellan databasen och applikationen
class TodoApi(private val dao: TaskDatabaseOperations) {

    // Hämtar alla uppgifter från databasen och konverterar dem till TaskWithSubTasks
    suspend fun getTasks(): List<TaskWithSubTasks> {
        val tasks = dao.getAllTasks().firstOrNull() // Hämtar den första listan från Flow
        return tasks?.map { entity ->
            TaskWithSubTasks(
                id = entity.id,
                taskName = entity.taskName,
                completed = false, // Standardvärde för completed
                subTasks = entity.subTasks // Kopplade deluppgifter
            )
        } ?: emptyList() // Returnerar en tom lista om inget hittas
    }

    // Lägger till en ny uppgift i databasen
    suspend fun addTask(task: TaskWithSubTasks) {
        dao.insertTask(
            TodoEntity(
                id = task.id, // ID för uppgiften
                taskName = task.taskName, // Namnet på uppgiften
                subTasks = task.subTasks // Lista över deluppgifter
            )
        )
    }

    // Uppdaterar en befintlig uppgift i databasen
    suspend fun updateTask(task: TaskWithSubTasks) {
        dao.updateTask(
            TodoEntity(
                id = task.id, // ID för uppgiften som ska uppdateras
                taskName = task.taskName, // Uppdaterat namn på uppgiften
                subTasks = task.subTasks // Uppdaterad lista över deluppgifter
            )
        )
    }

    // Tar bort en specifik uppgift från databasen baserat på dess ID
    suspend fun deleteTask(taskId: Int) {
        val tasks = dao.getAllTasks().firstOrNull() // Hämtar den aktuella listan från databasen
        val entityToDelete = tasks?.find { it.id == taskId } // Hittar rätt uppgift baserat på ID
        entityToDelete?.let { dao.deleteTask(it) } // Tar bort uppgiften om den finns
    }
}
