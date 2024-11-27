package com.example.todoapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.todoapp.api.SubTask

// Entitet som representerar en huvuduppgift i databasen
@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Automatiskt genererat ID
    val taskName: String, // Namn på huvuduppgiften
    @TypeConverters(SubTaskConverter::class) val subTasks: List<SubTask> = emptyList() // Lista av deluppgifter, konverterad med SubTaskConverter
)
