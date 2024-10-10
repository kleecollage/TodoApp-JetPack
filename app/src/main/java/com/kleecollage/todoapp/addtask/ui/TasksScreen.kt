package com.kleecollage.todoapp.addtask.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kleecollage.todoapp.addtask.ui.model.TaskModel

@Composable
fun TasksScreen(taskViewModel: TaskViewModel, innerPadding: PaddingValues) {
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        AddTaskDialog(
            showDialog,
            onDismiss = {taskViewModel.onShowDialog(false) },
            onTaskAdded = {taskViewModel.onTasksCreated(it) }
        )
        FabDialog(Modifier.align(Alignment.BottomEnd).padding(16.dp), taskViewModel)
        TaskList(taskViewModel)
    }
}

@Composable
fun  FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = { taskViewModel.onShowDialog(true) },
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss:() -> Unit, onTaskAdded:(String) -> Unit ) {
    var myTask by remember { mutableStateOf("") }

    if(show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(Modifier.fillMaxWidth().background(Color.White).padding(16.dp)) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTask,
                    onValueChange = {myTask = it},
                    singleLine = true,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { onTaskAdded(myTask); myTask=""  },
                    modifier = Modifier.fillMaxWidth())
                {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }
}

@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val myTasks:List<TaskModel> = taskViewModel.task
    LazyColumn {
       items(myTasks, key = {it.id} ) { task ->
           ItemTask(task, taskViewModel)
       }
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel){
    Card( Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit){
                detectTapGestures(onLongPress = {
                    taskViewModel.onItemRemove(taskModel)
                })
            },
        // border = BorderStroke(2.dp, Color.DarkGray),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = taskModel.task,
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = {taskViewModel.onCheckBocSelected(taskModel)}
            )
        }
    }
}

















