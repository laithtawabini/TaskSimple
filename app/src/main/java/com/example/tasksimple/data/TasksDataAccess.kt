package com.example.tasksimple.data

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasksimple.UI.MainActivity

@RequiresApi(Build.VERSION_CODES.P)
class TasksDataAccess {
    private var taskList = mutableListOf<Task>()
    private val tasks = MutableLiveData<List<Task>>()
    private val databaseHelper: DatabaseHelper = DatabaseHelper(MainActivity.appContext)
    private val db: SQLiteDatabase = databaseHelper.writableDatabase

    init {
        taskList = databaseHelper.getAllTasks(db, false)
        tasks.value = taskList
    }

    fun insertNewTask(task: Task) {
        val taskID = databaseHelper.insertNewTask(db, task)
        task.id = taskID
        taskList.add(task)
        tasks.value = taskList
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
        tasks.value = taskList
        databaseHelper.deleteTask(db, task)
    }

    fun getTasks() = tasks as LiveData<List<Task>>

    fun updateTask(task: Task): Boolean {
        tasks.value = taskList
        return databaseHelper.updateTask(db, task)
    }
}