package com.example.data.repository

import com.example.data.local.TodoDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toDto
import com.example.data.mapper.toEntity
import com.example.data.remote.ApiService
import com.example.domain.model.Todo
import com.example.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val apiService: ApiService,
    private val todoDao: TodoDao
) : TodoRepository {

    override suspend fun fetchTodos(): List<Todo> {
        val response = apiService.getTodos()
        if (response.isSuccessful) {
            val domainList = response.body()
                ?.todos
                ?.map { it.toDomain() }
                ?: emptyList()
            todoDao.insertAll(domainList.map { it.toEntity() })
            return domainList
        }
        throw Exception("Network error: ${response.code()}")
    }

    override suspend fun getTodos(): List<Todo> =
        todoDao.getAll().map { it.toDomain() }

    override suspend fun getAllTodos(): List<Todo> = getTodos()

    override suspend fun updateTodo(todo: Todo) {
        todoDao.update(todo.toEntity())
        apiService.updateTodo(todo.id, todo.toDto())
    }

    override suspend fun getTodosFromDb(): List<Todo> {
        return todoDao.getAll().map { it.toDomain() }
    }

    override suspend fun getTodoById(id: Int): Todo? =
        todoDao.getById(id)?.toDomain()
}