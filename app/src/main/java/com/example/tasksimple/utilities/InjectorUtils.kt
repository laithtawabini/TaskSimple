package com.example.tasksimple.utilities

import com.example.tasksimple.UI.tasks.TasksViewModelFactory
import com.example.tasksimple.data.TaskDatabase
import com.example.tasksimple.data.TaskRepository

object InjectorUtils {
    fun provideTasksViewModelFactory(): TasksViewModelFactory {
        val taskRepository = TaskRepository
            .getInstance(
                TaskDatabase.getInstance().tasksDataAccess
            )
        return TasksViewModelFactory(TaskRepository.getInstance(TaskDatabase.getInstance().tasksDataAccess))
    }
}