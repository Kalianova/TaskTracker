package ru.kalianova.rpgtracker

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.get
import androidx.room.Room
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityTaskBinding
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.*
import ru.kalianova.rpgtracker.model.TaskType.ColorAdapter
import ru.kalianova.rpgtracker.model.TaskType.ColorList
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter
import java.util.*


class TaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskBinding
    var selectedTask: Task? = null
    var selectedColor: TaskType? = null
    var selectedProject: Project? = null
    var selectedPriority: Priority? = null
    private val borrowBox = ObjectBox.boxStore.boxFor(TaskType::class.java)
    private val borrowBoxTask = ObjectBox.boxStore.boxFor(Task::class.java)
    private val borrowBoxProject = ObjectBox.boxStore.boxFor(Project::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSpinner()
    }

    private fun loadSpinner() {
        var color = "#000000"
        GlobalScope.launch {
            if (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
                    .equals(Configuration.UI_MODE_NIGHT_YES)
            )
                color = "#FFFFFF"

            var listColor = listOf(
                TaskType(
                    0,
                    "default",
                    color
                )
            ).plus(borrowBox.all)

            GlobalScope.launch {
                var listPriority = Priority().priorityList()
                selectedPriority = listPriority[0]
                binding.priorityPicker.setAdapter(
                    PriorityAdapter(
                        applicationContext, listPriority
                    )
                )

                binding.priorityPicker.apply {

                    setSelection(0, 0)
                    onItemClickListener = object : AdapterView.OnItemClickListener {
                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            position: Int,
                            p3: Long
                        ) {
                            selectedPriority = listPriority[position]
                        }

                    }
                }

            }
            GlobalScope.launch {
                var listTask = borrowBoxTask.all
                if (listTask.size != 0) {
                    binding.editTextTask.setAdapter(
                        TaskAdapter(
                            applicationContext, listTask
                        )
                    )

                    binding.editTextTask.apply {
                        setSelection(0, 0)
                        onItemClickListener = object : AdapterView.OnItemClickListener {
                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                position: Int,
                                p3: Long
                            ) {
                                selectedTask = listTask[position]
                                /*binding.editTextTaskName.setText(selectedTask?.name)
                                binding.editTextTaskDescription.setText(selectedTask?.description)
                                selectedColor = borrowBox.get(selectedTask!!.type)
                                binding.textViewTaskTaskTypeSpinner.setText(
                                    selectedColor?.name,
                                    false
                                )
                                binding.viewColorTask.setBackgroundColor(
                                    Color.parseColor(
                                        selectedColor?.color ?: "#FFFFFF"
                                    )
                                )
                                selectedProject = borrowBoxProject.get(selectedTask!!.project)
                                binding.textViewProject.setText(selectedProject?.name, false)
                                selectedPriority =
                                    Priority().priorityList()[selectedTask!!.priority]
                                binding.priorityPicker.setText(selectedPriority?.name, false)
                                if (selectedTask?.deadline != null) {
                                    binding.deadlineSwitch.isChecked = true
                                    binding.datePickerTask.updateDate(
                                        selectedTask?.deadline!!.year,
                                        selectedTask?.deadline!!.month,
                                        selectedTask?.deadline!!.day
                                    )
                                }
                                binding.globalSwitch.isChecked = selectedTask?.global != null
                           */ }
                        }
                    }
                }
            }

            GlobalScope.launch {
                var listColor = borrowBox.all
                if (listColor.size != 0) {
                    selectedColor = listColor[0]
                    binding.textViewTaskTaskTypeSpinner.setAdapter(
                        TaskTypeAdapter(
                            applicationContext, listColor
                        )
                    )
                    binding.textViewTaskTaskTypeSpinner.apply {

                        setSelection(0, 0)

                        onItemClickListener = object : AdapterView.OnItemClickListener {

                            override fun onItemClick(
                                p0: AdapterView<*>?,
                                p1: View?,
                                position: Int,
                                p3: Long
                            ) {
                                selectedColor = listColor[position]
                                binding.viewColorTask.setBackgroundColor(
                                    Color.parseColor(
                                        listColor[position].color ?: "#FFFFFF"
                                    )
                                )
                            }

                        }
                    }
                }
            }

        }
        GlobalScope.launch {
            var listProject = borrowBoxProject.all
            if (listProject.size != 0) {
                selectedProject = listProject[0]
                binding.textViewProject.setAdapter(
                    ProjectAdapter(
                        applicationContext, listProject
                    )
                )

                binding.textViewProject.apply {

                    setSelection(0, 0)

                    onItemClickListener = object : AdapterView.OnItemClickListener {

                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            position: Int,
                            p3: Long
                        ) {
                            selectedProject = listProject[position]
                        }

                    }
                }
            }
        }


    }

    fun clickCreateTask(view: View) {
        val description = binding.editTextTaskDescription.text.toString()
        val name = binding.editTextTaskName.text.toString()

        val deadline = if (binding.deadlineSwitch.isChecked) Date(
            binding.datePickerTask.year,
            binding.datePickerTask.month,
            binding.datePickerTask.dayOfMonth
        ) else null
        val global = binding.deadlineSwitch.isChecked

        if (name.isEmpty() || selectedColor == null || selectedProject == null || selectedPriority == null) {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBoxTask.put(
                    Task(
                        0,
                        name,
                        description,
                        selectedColor!!.id,
                        selectedProject!!.id,
                        selectedPriority!!.id,
                        deadline,
                        null,
                        null ///////////global index
                    )
                )
            }
            finish()
        }
    }

    fun clickDeleteTask(view: View) {
        if (selectedTask == null) {
            Toast.makeText(this, "Задание не выбрано", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBoxTask.remove(selectedTask)
            }
            finish()
        }
    }

    fun clickUpdateTask(view: View) {
        if (binding.editTextTaskName.text.toString()
                .isEmpty() || selectedColor == null || selectedProject == null || selectedPriority == null
        ) {
            Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        } else if (selectedTask == null) {
            Toast.makeText(this, "Задание не выбрано", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                val deadline = if (binding.deadlineSwitch.isChecked) Date(
                    binding.datePickerTask.year,
                    binding.datePickerTask.month,
                    binding.datePickerTask.dayOfMonth
                ) else null
                borrowBoxTask.put(
                    Task(
                        selectedTask!!.id,
                        binding.editTextTaskName.text.toString(),
                        binding.editTextTaskDescription.text.toString(),
                        selectedColor!!.id,
                        selectedProject!!.id,
                        selectedPriority!!.id,
                        deadline,
                        null,
                        null ///////////global index
                    )
                )

            }
            finish()
        }
    }
}