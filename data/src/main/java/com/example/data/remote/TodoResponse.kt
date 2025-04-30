package com.example.data.remote

import com.example.data.model.TodoDto

data class TodoResponse(
    val todos: List<TodoDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)