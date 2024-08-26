package com.irempamukcu.todo

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irempamukcu.todo.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter


class TaskItemAdapter(
    val taskItems: MutableList<TaskItem>,
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int {
        return taskItems.size
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    fun removeItem(position: Int) {
        // Remove item from the list and notify the adapter
        taskItems.removeAt(position)
        notifyItemRemoved(position)
    }
}


class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name

        if (taskItem.isCompleted()) {
            binding.name.paintFlags = binding.name.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = binding.dueTime.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.name.paintFlags = binding.name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            binding.dueTime.paintFlags = binding.dueTime.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        binding.completeButton.apply {
            setImageResource(taskItem.imageResource())
            setColorFilter(taskItem.imageColor(context))
            setOnClickListener { clickListener.completeTaskItem(taskItem) }
        }

        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        binding.dueTime.text = taskItem.dueTime()?.let { timeFormat.format(it) } ?: " "
    }
}
