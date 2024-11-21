package com.example.todoapp.database
import androidx.room.Entity
import androidx.room.PrimaryKey

// En Entitet som representerar en tabell för våra To-Do-uppgifter
@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-genererat ID
    val task: String // Själva uppgiften
)
