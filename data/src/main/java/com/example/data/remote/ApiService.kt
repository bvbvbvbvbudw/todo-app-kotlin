package com.example.data.remote

import com.example.data.model.TodoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): Response<TodoResponse>

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: TodoDto
    ): Response<TodoDto>
}