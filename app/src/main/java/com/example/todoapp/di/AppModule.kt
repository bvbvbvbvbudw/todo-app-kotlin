package com.example.todoapp.di

import androidx.room.Room
import com.example.data.remote.ApiClient
import com.example.data.local.TodoDatabase
import com.example.data.remote.ApiService
import com.example.domain.repository.TodoRepository
import com.example.data.repository.TodoRepositoryImpl
import com.example.domain.usecase.FetchTodosUseCase
import com.example.domain.usecase.GetTodoByIdUseCase
import com.example.domain.usecase.GetTodosFromDbUseCase
import com.example.domain.usecase.UpdateTodoUseCase
import com.example.todoapp.viewmodel.TodoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            TodoDatabase::class.java,
            "todos.db"
        ).build()
    }
    single { get<TodoDatabase>().todoDao() }
    single { ApiClient.getRetrofitInstance().create(ApiService::class.java) }
    single<TodoRepository> { TodoRepositoryImpl(get(), get()) }
    single { FetchTodosUseCase(get()) }
    single { GetTodoByIdUseCase(get()) }
    single { UpdateTodoUseCase(get()) }
    factory { GetTodosFromDbUseCase(get()) }
    viewModel { TodoViewModel(get(), get(), get(), get()) }
}