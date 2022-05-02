package ru.kalianova.rpgtracker

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.room.Room
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityTaskBinding
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.Task
import ru.kalianova.rpgtracker.model.TaskType.ColorAdapter
import ru.kalianova.rpgtracker.model.TaskType.ColorList
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter


class TaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskBinding
    lateinit var selectedColor: TaskType
    private val borrowBox = ObjectBox.boxStore.boxFor(TaskType::class.java)
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

    }
}