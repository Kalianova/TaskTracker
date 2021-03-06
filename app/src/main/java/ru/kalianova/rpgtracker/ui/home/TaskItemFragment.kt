package ru.kalianova.rpgtracker.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import ru.kalianova.rpgtracker.R
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.Project
import ru.kalianova.rpgtracker.model.Task
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import io.objectbox.query.QueryBuilder
import ru.kalianova.rpgtracker.MainActivity
import ru.kalianova.rpgtracker.TaskActivity
import ru.kalianova.rpgtracker.model.Task_


class TaskItemFragment : Fragment() {

    private var columnCount = 1
    lateinit var adapterTask: TaskItemRecyclerViewAdapter
    private val borrowBoxTask = ObjectBox.boxStore.boxFor(Task::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        adapterTask = TaskItemRecyclerViewAdapter(getTask())
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = adapterTask //Add tasklist from database
            }
        }
        return view
    }


    fun getTask(): MutableList<Task> {
        val query = borrowBoxTask.query().order(Task_.priority).build()
        val res = query.find()
        query.close()
        return res
    }

    override fun onResume() {
        super.onResume()
        adapterTask.notifyDataSetChanged()

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TaskItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }



}