package com.example.tasksimple.UI.adapters

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksimple.R
import com.example.tasksimple.UI.MainActivity
import com.example.tasksimple.UI.UpdateTaskActivity
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.data.Task
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.*

class CurrentTasksAdapter(private val taskList: List<Task>, private val tasksViewModel: TasksViewModel) : RecyclerView.Adapter<CurrentTasksAdapter.TasksViewHolder>() {

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox    = itemView.findViewById(R.id.taskCardCheckBox)
        val title: TextView       = itemView.findViewById(R.id.taskCardTitle)
        val date: TextView        = itemView.findViewById(R.id.taskCardDueDate)
        val description: TextView = itemView.findViewById(R.id.taskCardDescription)
        val taskCardUpdateDataBtn: ImageButton = itemView.findViewById(R.id.taskCardUpdateDataBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TasksViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = taskList.get(position)
        holder.checkBox.isChecked = if (task.isFinished > 0) true else false
        holder.title.text         = task.name.uppercase()
        holder.description.text   = task.description
        val remainingDueDays: String = getDaysUntilDue(task.dueDate)

        if(remainingDueDays.toInt() < 0) {
            holder.date.setTextColor(Color.RED)
            holder.date.setShadowLayer(10f, 0f, 0f, Color.RED)
            holder.date.text = "Task is due!"
        }
        else if (remainingDueDays.toInt() == 0) {
            holder.date.setTextColor(Color.YELLOW)
            holder.date.setShadowLayer(10f, 0f, 0f, Color.YELLOW)
            holder.date.text = "Due today!"
        }
        else {
            holder.date.setTextColor(Color.GREEN)
            holder.date.setShadowLayer(10f, 0f, 0f, Color.GREEN)
            holder.date.text = "Due in $remainingDueDays days"
        }



        holder.checkBox.setOnClickListener {
            task.isFinished = 1
            tasksViewModel.updateTask(task)
        }

        holder.taskCardUpdateDataBtn.setOnClickListener {
            val intent = Intent(MainActivity.appContext, UpdateTaskActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("taskID", task.id)
            }

            MainActivity.appContext.startActivity(intent)
        }

        println("Added task ${holder.title.text}")
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    private fun getDaysUntilDue(taskDueDate: String): String {
        val nonFormattedCurrentDate = Calendar.getInstance().time
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = dateFormatter.format(nonFormattedCurrentDate)

        val (taskDueDay, taskDueMonth, taskDueYear) = taskDueDate.split('/')
        val (currentDay, currentMonth, currentYear) = currentDate.split('/')

        val daysDifference = LocalDate.of(taskDueYear.toInt(), taskDueMonth.toInt(), taskDueDay.toInt()).toEpochDay() -
                LocalDate.of(currentYear.toInt(), currentMonth.toInt(), currentDay.toInt()).toEpochDay()

        return daysDifference.toString()
    }
}