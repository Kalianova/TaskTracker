package ru.kalianova.rpgtracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kalianova.rpgtracker.R
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.Task
import java.util.*

class ListProjectAdapter(
    private val itemTasks: MutableList<Task>,
    val clickListener: (Task) -> Unit
) :

    RecyclerView.Adapter<ListProjectAdapter.ItemViewHolder>() {
    private val borrowBoxTask = ObjectBox.boxStore.boxFor(Task::class.java)
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var checkBox: CheckBox? = null
        var date: TextView? = null

        init {
            textView = itemView.findViewById(R.id.checkBoxTextView)
            checkBox = itemView.findViewById(R.id.checkBoxTask)
            date = itemView.findViewById(R.id.item_date_task)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_task_item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView?.text = itemTasks[position].name
        holder.checkBox?.isChecked = itemTasks[position].done != null
        holder.textView?.setOnClickListener{clickListener(itemTasks[position])}
        holder.checkBox?.setOnClickListener {
            if (holder.checkBox!!.isChecked)
                itemTasks[position].done = Date()
            else
                itemTasks[position].done = null
            borrowBoxTask.put(itemTasks[position])
        }
        holder.date?.text =
            if (itemTasks[position].deadline == null) "" else "${itemTasks[position].deadline?.day}.${itemTasks[position].deadline?.month}.${itemTasks[position].deadline?.year}"
    }

    override fun getItemCount() = itemTasks.size

}

