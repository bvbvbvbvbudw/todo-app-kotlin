package com.example.domain.usecase

import com.example.domain.repository.TodoRepository

class FetchTodosUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke() = repository.fetchTodos()
}