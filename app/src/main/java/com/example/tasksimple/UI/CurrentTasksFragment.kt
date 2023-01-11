package com.example.tasksimple.UI

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksimple.R
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.UI.adapters.CurrentTasksAdapter
import com.example.tasksimple.utilities.InjectorUtils


class CurrentTasksFragment : Fragment(R.layout.fragment_current_tasks) {
    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.currentTasksReciyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val factory = InjectorUtils.provideTasksViewModelFactory()
        val tasksViewModel = ViewModelProvider(this, factory).get(TasksViewModel::class.java)

        tasksViewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            recyclerView.adapter = CurrentTasksAdapter(tasks.filter { it.isFinished == 0 }.sortedByDescending { it.priority }, tasksViewModel)
        })
    }
}



