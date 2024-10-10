package com.kleecollage.todoapp.addtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val task: String,
    var selected: Boolean = false,
)