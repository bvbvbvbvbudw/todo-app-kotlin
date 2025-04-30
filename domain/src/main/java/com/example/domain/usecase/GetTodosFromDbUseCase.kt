package com.example.domain.usecase

import com.example.domain.model.Todo
import com.example.domain.repository.TodoRepository

class GetTodosFromDbUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(): List<Todo> = repository.getTodosFromDb()
}