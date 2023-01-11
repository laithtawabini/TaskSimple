package com.example.tasksimple.UI

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.tasksimple.R
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.data.Task
import com.example.tasksimple.utilities.InjectorUtils


class NewTaskDialog : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_task_details, container, false)

        var title: String;
        var description: String;
        var priority: String = "Low Priority";

        val taskTitle = view.findViewById<EditText>(R.id.taskTitleUpdate)
        val taskDescription = view.findViewById<EditText>(R.id.taskDescriptionUpdate)
        val taskDueDate = view.findViewById<DatePicker>(R.id.taskDueDateUpdate)
        val cancelBtn = view.findViewById<Button>(R.id.taskUpdateCancel)
        val submitBtn = view.findViewById<Button>(R.id.taskUpdateSubmit)

        val taskPriorityOptions = arrayOf("Low Priority", "Medium Priority", "High Priority")
        val prioritySpinner = view.findViewById<Spinner>(R.id.taskPriorityUpdate)
        prioritySpinner.adapter = activity?.let {
            ArrayAdapter(
                it,
                R.layout.support_simple_spinner_dropdown_item,
                taskPriorityOptions
            )
        } as SpinnerAdapter

        prioritySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //priority = prioritySpinner.get(position)
                priority = taskPriorityOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }

        submitBtn.setOnClickListener {
            title = taskTitle.text.toString()
            description = taskDescription.text.toString()
            //priority already obtained
            val day: Int = taskDueDate.getDayOfMonth()
            val month: Int = taskDueDate.getMonth() + 1
            val year: Int = taskDueDate.getYear()

            val task: Task = Task(-1, title, description, taskPriorityOptions.indexOf(priority), 0, "$day/$month/$year")

            val factory = InjectorUtils.provideTasksViewModelFactory()
            val viewModel = ViewModelProvider(this, factory).get(TasksViewModel::class.java)
            viewModel.insertNewTask(task)
            Toast.makeText(MainActivity.appContext, "Task Added!", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        return view
    }


}