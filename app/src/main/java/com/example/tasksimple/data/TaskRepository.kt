package com.example.tasksimple.data

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class TaskRepository private constructor(private val tasksDataAccess: TasksDataAccess) {

    fun insertNewTask(task: Task) {
        tasksDataAccess.insertNewTask(task)
    }

    fun deleteTask(task: Task) {
        tasksDataAccess.deleteTask(task)
    }

    fun getTasks() = tasksDataAccess.getTasks()

    fun updateTask(task: Task): Boolean {
        return tasksDataAccess.updateTask(task)
    }
    companion object {
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(tasksDataAccess: TasksDataAccess) =
            instance ?: synchronized(this) {
                instance ?: TaskRepository(tasksDataAccess).also { instance = it }
            }
    }
}