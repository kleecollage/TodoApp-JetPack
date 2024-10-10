package com.kleecollage.todoapp.addtask.ui

import com.kleecollage.todoapp.addtask.ui.model.TaskModel

sealed interface TasksUiState {
    object Loading: TasksUiState
    data class Error(val throwable: Throwable): TasksUiState
    data class Success(val task:List<TaskModel>): TasksUiState
}