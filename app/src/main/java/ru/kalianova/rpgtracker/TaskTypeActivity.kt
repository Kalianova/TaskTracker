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
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityTaskBinding
import ru.kalianova.rpgtracker.databinding.ActivityTaskTypeBinding
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.TaskType.ColorAdapter
import ru.kalianova.rpgtracker.model.TaskType.ColorList
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter
import java.lang.IllegalArgumentException

class TaskTypeActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskTypeBinding
    lateinit var selectedColor: TaskType
    lateinit var viewColor: View
    lateinit var name: EditText
    private val borrowBox = ObjectBox.boxStore.boxFor(TaskType::class.java)

    //private lateinit var borrowQuery: Query<TaskType>
    //private lateinit var subscription: DataSubscription

    var color: String = "#FFFFFF"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewColor = binding.viewColor
        viewColor.setBackgroundColor(Color.parseColor(color))

        /* borrowQuery = borrowBox.query().build()
         subscription = borrowQuery
             .subscribe()
             .on(AndroidScheduler.mainThread())
             .observer {notes -> TaskTypeAdapter.setBorrowedItemList(notes) }*/

        name = binding.editTextTaskTypeName
        loadSpinner()

    }

    fun clickAddTaskTypeDB(view: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Название не заполнено", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBox.put(TaskType(0, name.text.toString(), color))
            }
            finish()
        }

    }

    fun clickUpdateTaskType(view: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Название не заполнено", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBox.put(TaskType(selectedColor.id, name.text.toString(), color))
            }
            finish()
        }

    }

    fun clickDeleteTaskType(view: View) {
        GlobalScope.launch {
            borrowBox.remove(selectedColor)
        }
        finish()
    }


    private fun loadSpinner() {
        var positionColor = 0
        var colorList = ColorList().basicColors()
        binding.textViewTaskTypeColorSpinner.setAdapter(
            ColorAdapter(
                applicationContext, colorList
            )
        )
        binding.textViewTaskTypeColorSpinner.apply {
            setSelection(0)
            onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    color = colorList[position].code
                    viewColor.setBackgroundColor(Color.parseColor(colorList[position].code))
                }

            }


        }
        GlobalScope.launch {
            var listColor = borrowBox.all


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
                            binding.textViewTaskTypeColorSpinner.setText(colorList.find {
                                it.code.equals(
                                    selectedColor.color
                                )
                            }?.name ?: "", false)
                            color = selectedColor.color
                            viewColor.setBackgroundColor(Color.parseColor(color))
                            name.setText(selectedColor.name)
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {}

                    }
                }
            }
        }

    }


}