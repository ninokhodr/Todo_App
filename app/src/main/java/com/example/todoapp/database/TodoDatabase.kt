package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Room-databasklass som innehåller TodoEntity och TaskDatabaseOperations
@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    // Ger åtkomst till DAO-funktionerna
    abstract fun taskDatabaseOperations(): TaskDatabaseOperations

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        // Skapar en synkad instans av databasen om den inte redan finns för att undvika konflikter
        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database" // Namnet på databasen
                )
                    .fallbackToDestructiveMigration() // Raderar och återskapar databasen om det sker ngra ändringar i dess struktur
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
