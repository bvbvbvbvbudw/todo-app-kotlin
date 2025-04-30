package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.TodoViewModel
import org.koin.androidx.compose.getViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    navController: NavController,
    todoId: Int,
    viewModel: TodoViewModel = getViewModel()
) {
    val todo by viewModel.getTodoById(todoId).collectAsState(initial = null)
    var editedText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var hasNavigatedBack by remember { mutableStateOf(false) }

    LaunchedEffect(todo) {
        todo?.let {
            editedText = it.todo
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODO details") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (editedText != todo?.todo) {
                            showDialog = true
                        } else if (!hasNavigatedBack) {
                            hasNavigatedBack = true
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (todo != null && !todo!!.completed) {
                Button(
                    onClick = {
                        viewModel.updateTodoText(editedText)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Save")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (todo != null) {
                if (todo!!.completed) {
                    Text(text = todo!!.todo)
                } else {
                    OutlinedTextField(
                        value = editedText,
                        onValueChange = { editedText = it },
                        label = { Text("Edit TODO") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmation") },
                text = { Text("Are you sure want to leave? All edited data will be lost") },
                confirmButton = {
                    TextButton(onClick = {
                        if (!hasNavigatedBack) {
                            hasNavigatedBack = true
                            navController.popBackStack()
                        }
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}