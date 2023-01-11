package com.example.tasksimple.UI.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.tasksimple.data.Task
import com.example.tasksimple.data.TaskRepository

@RequiresApi(Build.VERSION_CODES.P)
class TasksViewModel(private val taskRepository: TaskRepository): ViewModel() {
    fun getTasks() = taskRepository.getTasks()
    fun insertNewTask(task: Task) = taskRepository.insertNewTask(task)
    fun deleteTask(task: Task) = taskRepository.deleteTask(task)
    fun updateTask(task: Task): Boolean = taskRepository.updateTask(task)
}