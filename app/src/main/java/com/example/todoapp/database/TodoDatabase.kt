package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Room databasklass som hanterar åtkomst till databasen
@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
@TypeConverters(SubTaskConverter::class) // Använder konverterare för deluppgifter
abstract class TodoDatabase : RoomDatabase() {

    // Ger tillgång till DAO för databasoperationer
    abstract fun taskDatabaseOperations(): TaskDatabaseOperations

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        // Säkerställer en enda instans av databasen
        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database" // Namn på databasen
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
