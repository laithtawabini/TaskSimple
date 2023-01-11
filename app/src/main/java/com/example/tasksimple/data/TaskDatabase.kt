package com.example.tasksimple.data

import android.os.Build
import androidx.annotation.RequiresApi

class TaskDatabase private constructor() {

    @RequiresApi(Build.VERSION_CODES.P)
    val tasksDataAccess = TasksDataAccess()

    companion object {
        @Volatile private var instance: TaskDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: TaskDatabase().also { instance = it }
            }
    }


}