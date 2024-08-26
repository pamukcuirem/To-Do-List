package com.irempamukcu.todo

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.irempamukcu.todo.databinding.FragmentNewTaskSheetBinding
import java.time.LocalTime

class NewTaskSheet(private var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        if (taskItem != null) {
            binding.textView.text = "Düzenle"
            binding.name.text = Editable.Factory.getInstance().newEditable(taskItem!!.name)

            taskItem?.dueTime()?.let {
                dueTime = it
                updateTimeButtonText()
            }
        } else {
            binding.textView.text = "Yapılacak Ekle"
        }
    }

    private fun setupListeners() {
        binding.pigeonButton.setOnClickListener {
            saveAction()
        }

        binding.datePicker.setOnClickListener {
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if (dueTime == null) dueTime = LocalTime.now()

        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }

        TimePickerDialog(requireContext(), listener, dueTime!!.hour, dueTime!!.minute, true).apply {
            setTitle("Vakit Seç")
            show()
        }
    }

    private fun updateTimeButtonText() {
        binding.datePicker.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val dueTimeString = dueTime?.let { TaskItem.timeFormatter.format(it) }

        if (taskItem == null) {
            val newTask = TaskItem(name, dueTimeString, null)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskItem!!.apply {
                this.name = name
                this.dueTimeString = dueTimeString
            }
            taskViewModel.updateTaskItem(taskItem!!)
        }

        binding.name.setText("")
        dismiss()
    }
}
