package com.example.data.mapper

import com.example.data.local.TodoEntity
import com.example.data.model.TodoDto
import com.example.domain.model.Todo

fun TodoDto.toDomain() = Todo(id, todo, completed, userId)
fun TodoEntity.toDomain() = Todo(id, todo, completed, userId)
fun Todo.toEntity() = TodoEntity(id, todo, completed, userId)
fun Todo.toDto() = TodoDto(id, todo, completed, userId)