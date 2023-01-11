package com.example.tasksimple.UI

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasksimple.R
import com.example.tasksimple.UI.tasks.TasksViewModel
import com.example.tasksimple.utilities.InjectorUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        appContext = applicationContext
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        changeFragment(CurrentTasksFragment())


        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavView.background = null

        bottomNavView.setOnNavigationItemReselectedListener {
           return@setOnNavigationItemReselectedListener
        }

        bottomNavView.setOnNavigationItemSelectedListener {
            item->
                if(item.itemId == R.id.current_tasks)
                    changeFragment(CurrentTasksFragment())
                else if (item.itemId == R.id.previous_tasks)
                    changeFragment(PreviousTasksFragment())


            return@setOnNavigationItemSelectedListener true
        }


        val addTaskBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addTaskBtn.setOnClickListener {
            NewTaskDialog().show(supportFragmentManager, "Add New Task")
        }

    }

    companion object {
        lateinit  var appContext: Context
    }


    private fun changeFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}