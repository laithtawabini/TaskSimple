package com.example.tasksimple.UI.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasksimple.data.TaskRepository

class TasksViewModelFactory(private val taskRepository: TaskRepository) :ViewModelProvider.NewInstanceFactory() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(taskRepository) as T
    }
}