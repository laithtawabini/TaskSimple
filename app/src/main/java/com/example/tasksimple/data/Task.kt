package com.example.tasksimple.data

import java.sql.Date

data class Task(var id: Long?, var name: String, var description: String, var priority: Int, var isFinished: Int, var dueDate: String)
