package ru.kalianova.rpgtracker

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityTaskBinding
import ru.kalianova.rpgtracker.db.TaskDatabase
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter

class TaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskBinding
    lateinit var selectedColor: TaskType
    lateinit var db: TaskDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = Room.databaseBuilder(applicationContext, TaskDatabase::class.java, "task")
            .build()
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
            ).plus(db.taskTypeDao().getAll())
            selectedColor = listColor[0]
            binding.editTextTypeSpinner.apply {
                adapter = TaskTypeAdapter(
                    applicationContext, listColor
                )
                setSelection(0, true)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        p3: Long
                    ) {
                        selectedColor = listColor[position]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
        }

    }
}