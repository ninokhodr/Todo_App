package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

// Ett DAO-gränssnitt för att hantera databasoperationer för uppgifter
@Dao
interface TaskDatabaseOperations {

    // Lägger till en ny uppgift
    @Insert
    suspend fun insertTask(todo: TodoEntity): Long

    // Tar bort en specifik uppgift
    @Delete
    suspend fun deleteTask(todo: TodoEntity): Int

    // Hämtar alla uppgifter, sorterade efter ID
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TodoEntity>>
}