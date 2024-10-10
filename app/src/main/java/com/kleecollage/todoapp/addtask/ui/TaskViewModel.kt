package com.kleecollage.todoapp.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kleecollage.todoapp.addtask.ui.model.TaskModel
import javax.inject.Inject

class TaskViewModel @Inject constructor(): ViewModel() {
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














