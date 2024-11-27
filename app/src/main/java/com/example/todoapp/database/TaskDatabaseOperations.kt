package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// DAO som hanterar databasoperationer för uppgifter
@Dao
interface TaskDatabaseOperations {

    // Infogar en ny uppgift och returnerar dess ID
    @Insert
    suspend fun insertTask(todo: TodoEntity): Long

    // Tar bort en specifik uppgift och returnerar antal rader som raderats
    @Delete
    suspend fun deleteTask(todo: TodoEntity): Int

    // Uppdaterar en befintlig uppgift i databasen
    @Update
    suspend fun updateTask(todo: TodoEntity): Int

    // Hämtar alla uppgifter från databasen, sorterade efter ID
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TodoEntity>>
}
