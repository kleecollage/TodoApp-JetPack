package com.kleecollage.todoapp.addtask.domain

import com.kleecollage.todoapp.addtask.data.TaskRepository
import com.kleecollage.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}