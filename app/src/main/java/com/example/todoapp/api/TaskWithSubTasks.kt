package com.example.todoapp.api

// Huvuddataobjekt för en uppgift med deluppgifter
data class TaskWithSubTasks(
    val id: Int,
    val taskName: String,
    val completed: Boolean,
    val subTasks: List<SubTask> = emptyList() // Default tom lista för att undvika null
)

// Dataobjekt för en deluppgift
data class SubTask(
    val id: Int,
    val name: String,
    val completed: Boolean
)
