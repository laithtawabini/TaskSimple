package com.example.tasksimple.UI.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksimple.R
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.data.Task


class PreviousTasksAdapter(private val taskList: List<Task>, private val tasksViewModel: TasksViewModel) : RecyclerView.Adapter<PreviousTasksAdapter.TasksViewHolder>() {

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView       = itemView.findViewById(R.id.finishedTaskCardTitle)
        val description: TextView = itemView.findViewById(R.id.finishedTaskCardDescription)
        val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteTaskBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.finished_task_item, parent, false)
        return TasksViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = taskList.get(position)
        holder.title.text         = task.name.uppercase()
        holder.description.text   = task.description
        holder.deleteBtn.setOnClickListener {
            tasksViewModel.deleteTask(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

}