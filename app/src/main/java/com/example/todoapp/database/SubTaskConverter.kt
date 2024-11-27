package com.example.todoapp.database

import androidx.room.TypeConverter
import com.example.todoapp.api.SubTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Omvandlar en lista med SubTasks till och från en JSON-sträng, används av room
class SubTaskConverter {

    // Konverterar en lista av SubTasks till en JSON-sträng
    @TypeConverter
    fun fromSubTaskList(subTasks: List<SubTask>): String {
        return try {
            Gson().toJson(subTasks) // Serialiserar listan till JSON
        } catch (e: Exception) {
            "[]" // Returnerar en tom lista som JSON-sträng vid fel
        }
    }

    // Konverterar en JSON-sträng till en lista av SubTasks
    @TypeConverter
    fun toSubTaskList(subTaskString: String): List<SubTask> {
        return try {
            val listType = object : TypeToken<List<SubTask>>() {}.type
            Gson().fromJson<List<SubTask>>(subTaskString, listType) ?: emptyList() // Deserialiserar JSON eller returnerar tom lista
        } catch (e: Exception) {
            emptyList() // Returnerar en tom lista vid fel
        }
    }
}
