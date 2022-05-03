package ru.kalianova.rpgtracker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityProjectBinding
import ru.kalianova.rpgtracker.databinding.ActivityTaskTypeBinding
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.Project
import ru.kalianova.rpgtracker.model.TaskType.ColorAdapter
import ru.kalianova.rpgtracker.model.TaskType.ColorList
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter
import android.R

import android.widget.AutoCompleteTextView

import android.widget.ArrayAdapter
import ru.kalianova.rpgtracker.model.ProjectAdapter


class project : AppCompatActivity() {
    lateinit var binding: ActivityProjectBinding
    var selectedProject: Project? = null
    lateinit var name: EditText
    private val borrowBox = ObjectBox.boxStore.boxFor(Project::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        name = binding.editTextProjectName
        loadSpinner()
    }

    fun clickUpdateProject(view: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Название не заполнено", Toast.LENGTH_SHORT).show()
        } else if (selectedProject == null) {
            Toast.makeText(this, "Проект не выбран", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBox.put(Project(selectedProject!!.id, name.text.toString()))
            }
            finish()
        }
    }
    fun clickDeleteProject(view: View) {
        if (selectedProject == null) {
            Toast.makeText(this, "Проект не выбран", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBox.remove(selectedProject)
            }
            finish()
        }
    }
    fun clickAddProjectDB(view: View) {
        if (name.text.isEmpty()) {
            Toast.makeText(this, "Название не заполнено", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch {
                borrowBox.put(Project(0, name.text.toString()))
            }
            finish()
        }
    }

    private fun loadSpinner() {

        GlobalScope.launch {
            var listProject = borrowBox.all
            if (listProject.size != 0) {
                binding.textViewProjectSpinner.setAdapter(
                    ProjectAdapter(
                        applicationContext, listProject
                    )
                )

                binding.textViewProjectSpinner.apply {

                    setSelection(0, 0)

                    onItemClickListener = object : AdapterView.OnItemClickListener {

                        override fun onItemClick(
                            p0: AdapterView<*>?,
                            p1: View?,
                            position: Int,
                            p3: Long
                        ) {
                            selectedProject = listProject[position]
                            name.setText(selectedProject?.name)
                        }

                    }
                }
            }
        }

    }

}