package com.example.domain.usecase

import com.example.domain.model.Todo
import com.example.domain.repository.TodoRepository

class UpdateTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo)
    }
}