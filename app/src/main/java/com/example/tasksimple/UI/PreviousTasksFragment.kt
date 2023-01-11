package com.example.tasksimple.UI

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksimple.R
import com.example.tasksimple.UI.adapters.CurrentTasksAdapter
import com.example.tasksimple.UI.adapters.PreviousTasksAdapter
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.utilities.InjectorUtils


class PreviousTasksFragment : Fragment(R.layout.fragment_previous_tasks) {

    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_previous_tasks, container, false)

        val factory = InjectorUtils.provideTasksViewModelFactory()
        val tasksViewModel = ViewModelProvider(this, factory).get(TasksViewModel::class.java)


        recyclerView = view.findViewById(R.id.previousTasksReciyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        tasksViewModel.getTasks().observe(viewLifecycleOwner, Observer { tasks ->
            recyclerView.adapter = PreviousTasksAdapter(tasks.filter { it.isFinished == 1 }, tasksViewModel)
        })

        return view
    }

}