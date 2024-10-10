package com.kleecollage.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kleecollage.todoapp.addtask.domain.AddTaskUseCase
import com.kleecollage.todoapp.addtask.domain.GetTasksUseCase
import com.kleecollage.todoapp.addtask.ui.TasksUiState.*
import com.kleecollage.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase, getTasksUseCase: GetTasksUseCase
): ViewModel() {
    val uiState: StateFlow<TasksUiState> = getTasksUseCase()
        .map ( ::Success )
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val task: List<TaskModel> = _tasks

    fun onShowDialog(show: Boolean) {
        _showDialog.value = show
    }

    fun onTasksCreated(task: String){
        _showDialog.value = false
        _tasks.add(TaskModel(task = task))

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onCheckBocSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(taskModel: TaskModel){
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }
}














