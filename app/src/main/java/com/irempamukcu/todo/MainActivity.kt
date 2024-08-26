package com.irempamukcu.todo

import SwipeToDeleteCallback
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.irempamukcu.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((applicationContext as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = TaskItemAdapter(mutableListOf(), this)
        binding.toDoListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = adapter

            // Attach the ItemTouchHelper to the RecyclerView
            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
            itemTouchHelper.attachToRecyclerView(this)
        }

        taskViewModel.taskItems.observe(this) {
            (binding.toDoListRecyclerView.adapter as TaskItemAdapter).apply {
                // Update the adapter with the new data
                taskItems.clear()
                taskItems.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}
