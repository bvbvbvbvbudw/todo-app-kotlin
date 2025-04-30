package com.example.domain.repository

import com.example.domain.model.Todo

interface TodoRepository {
    suspend fun fetchTodos(): List<Todo>
    suspend fun getTodos(): List<Todo>
    suspend fun getTodosFromDb(): List<Todo>
    suspend fun updateTodo(todo: Todo)
    suspend fun getTodoById(id: Int): Todo?
    suspend fun getAllTodos(): List<Todo>
}