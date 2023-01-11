package com.example.tasksimple.UI

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.tasksimple.R
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.data.Task
import com.example.tasksimple.utilities.InjectorUtils

class UpdateTaskActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        var titleView = findViewById<EditText>(R.id.taskTitleUpdate)
        var descriptionView = findViewById<EditText>(R.id.taskDescriptionUpdate)
        var priorityView = findViewById<Spinner>(R.id.taskPriorityUpdate)
        var dueDateView = findViewById<DatePicker>(R.id.taskDueDateUpdate)
        var submitBtnView = findViewById<Button>(R.id.taskUpdateSubmit)
        var cancelBtnView = findViewById<Button>(R.id.taskUpdateCancel)

        val taskPriorityOptions = arrayOf("Low Priority", "Medium Priority", "High Priority")

        var chosenPriority = ""

        priorityView.adapter = this.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                taskPriorityOptions
            )
        } as SpinnerAdapter

        priorityView.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //priority = prioritySpinner.get(position)
                chosenPriority = taskPriorityOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val factory = InjectorUtils.provideTasksViewModelFactory()
        val tasksViewModel = ViewModelProvider(this, factory).get(TasksViewModel::class.java)

        val bundle: Bundle? = intent.extras
        val tasks = tasksViewModel.getTasks().value

        var taskToUpdate: Task? = null

        //search for the task we want to update using its ID received when activity was changed
        for(task in tasks!!) {
            if(task.id == bundle?.get("taskID")) {
                taskToUpdate = task
                break
            }
        }

        titleView.setText(taskToUpdate?.name)
        descriptionView.setText(taskToUpdate?.description)

        val currPriorityIndex = taskToUpdate?.priority!!
        chosenPriority = taskPriorityOptions[currPriorityIndex]
        priorityView.setSelection(currPriorityIndex)

        //TODO: Receive a task ID that is meant to be updated, take input from the activity views then update it
        submitBtnView.setOnClickListener {
            //put data in task to update
            taskToUpdate.name = titleView.text.toString()
            taskToUpdate.description = descriptionView.text.toString()
            taskToUpdate.priority = taskPriorityOptions.indexOf(chosenPriority)
            val day: Int = dueDateView.getDayOfMonth()
            val month: Int = dueDateView.getMonth() + 1
            val year: Int = dueDateView.getYear()
            taskToUpdate.dueDate = "$day/$month/$year"
            //update
            tasksViewModel.updateTask(taskToUpdate)

            //return to main activity
            returnToMain()
        }

        cancelBtnView.setOnClickListener { returnToMain() }
    }

    private fun returnToMain() = startActivity(Intent(this, MainActivity::class.java))
}