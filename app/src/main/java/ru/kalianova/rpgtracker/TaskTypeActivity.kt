package ru.kalianova.rpgtracker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityTaskBinding
import ru.kalianova.rpgtracker.databinding.ActivityTaskTypeBinding
import ru.kalianova.rpgtracker.db.TaskDatabase
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter
import java.lang.IllegalArgumentException

class TaskTypeActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskTypeBinding
    lateinit var selectedColor: TaskType
    lateinit var viewColor: View
    lateinit var textColor: EditText
    lateinit var name: EditText
    lateinit var db: TaskDatabase
    var color: String = "#FFFFFF"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewColor = binding.viewColor1
        textColor = binding.editTextTaskTypeColor1
        viewColor.setBackgroundColor(Color.parseColor(color))
        textColor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                try {
                    var colorTry = Color.parseColor(p0.toString())
                    viewColor.setBackgroundColor(colorTry)
                    color = p0.toString()
                } catch (error: IllegalArgumentException) {
                    viewColor.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    color = "#FFFFFF"
                }
            }
        })

        name = binding.editTextTaskTypeName
        db = Room.databaseBuilder(applicationContext, TaskDatabase::class.java, "task")
            .build()
        loadSpinner()

    }

    fun clickAddTaskTypeDB(view: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Название не заполнено", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                db.taskTypeDao().addTask(TaskType(0, name.text.toString(), color))
            }
            finish()
        }

    }

    fun clickUpdateTaskType(view: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Название не заполнено", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                db.taskTypeDao()
                    .update(TaskType(selectedColor.id, name.text.toString(), color))
            }
            finish()
        }

    }

    fun clickDeleteTaskType(view: View) {
        GlobalScope.launch {
            db.taskTypeDao().delete(selectedColor)
        }
        finish()
    }


    private fun loadSpinner() {

        GlobalScope.launch {
            var listColor = db.taskTypeDao().getAll()
            if (listColor.size != 0) {
                selectedColor = listColor[0]
                binding.editTextTaskTypeSpinner.apply {
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
                            textColor.setText(selectedColor.color)
                            color = selectedColor.color
                            name.setText(selectedColor.name)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {}

                    }
                }
            }
        }

    }


}