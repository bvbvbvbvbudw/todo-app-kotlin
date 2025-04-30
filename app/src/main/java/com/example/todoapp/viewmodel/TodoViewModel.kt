package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Todo
import com.example.domain.usecase.FetchTodosUseCase
import com.example.domain.usecase.GetTodoByIdUseCase
import com.example.domain.usecase.GetTodosFromDbUseCase
import com.example.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val fetchTodosUseCase: FetchTodosUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val getTodosFromDbUseCase: GetTodosFromDbUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            try {
                val todosList = fetchTodosUseCase()
                _todos.value = todosList
            } catch (e: Exception) {
                android.util.Log.e("API_RESPONSE", "Failed to load todos: ${e.message}")
            }
        }
    }

    fun getTodoById(id: Int): StateFlow<Todo?> {
        viewModelScope.launch {
            _todo.value = getTodoByIdUseCase(id)
        }
        return todo
    }

    fun loadTodosFromDb() {
        viewModelScope.launch {
            _todos.value = getTodosFromDbUseCase()
        }
    }

    fun updateTodoText(updatedText: String) {
        viewModelScope.launch {
            _todo.value?.let {
                val updated = it.copy(todo = updatedText)
                updateTodoUseCase(updated)
                _todo.value = updated
                loadTodosFromDb()
            }
        }
    }
}