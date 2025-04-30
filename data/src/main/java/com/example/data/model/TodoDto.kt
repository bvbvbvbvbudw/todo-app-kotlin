package com.example.data.model

data class TodoDto(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)