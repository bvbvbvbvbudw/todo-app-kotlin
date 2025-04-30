package com.example.domain.usecase

import com.example.domain.model.Todo
import com.example.domain.repository.TodoRepository

class GetTodoByIdUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): Todo? {
        return repository.getTodoById(id)
    }
}