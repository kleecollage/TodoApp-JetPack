package com.kleecollage.todoapp.addtask.data.di

import android.content.Context
import androidx.room.Room
import com.kleecollage.todoapp.addtask.data.TaskDao
import com.kleecollage.todoapp.addtask.data.TodoDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun providesTaskDao(todoDatabase: TodoDataBase): TaskDao{
        return todoDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDataBase{
        return Room.databaseBuilder(
            appContext,
            TodoDataBase::class.java,
            name = "TaskDatabase"
        ).build()
    }
}