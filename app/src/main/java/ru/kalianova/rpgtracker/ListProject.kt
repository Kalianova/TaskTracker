package ru.kalianova.rpgtracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kalianova.rpgtracker.databinding.ActivityTaskTypeBinding
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.Task
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import ru.kalianova.rpgtracker.model.TaskType.TaskTypeAdapter
import ru.kalianova.rpgtracker.model.Task_
import ru.kalianova.rpgtracker.ui.ListProjectAdapter

class ListProject : AppCompatActivity() {

    private var id: Long? = 0
    private val borrowBox = ObjectBox.boxStore.boxFor(Task::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_project)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        id = intent.extras?.getLong("id")
        recyclerView.adapter = ListProjectAdapter(getTask()) {
            var intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }

    fun getTask(): MutableList<Task> {
        val query = borrowBox.query().equal(Task_.project, id!!).build()
        val res = query.find()
        query.close()
        return res
    }
}