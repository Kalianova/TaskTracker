package ru.kalianova.rpgtracker.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat.makeBasic
import androidx.core.content.ContextCompat.startActivity
import com.snappydb.DBFactory
import ru.kalianova.rpgtracker.TaskActivity

import ru.kalianova.rpgtracker.databinding.FragmentTaskItemBinding
import ru.kalianova.rpgtracker.db.ObjectBox
import ru.kalianova.rpgtracker.model.Task
import java.util.*

class TaskItemRecyclerViewAdapter(
    private val values: List<Task>,
    /*private val clickListener: onClickListener*/
) : RecyclerView.Adapter<TaskItemRecyclerViewAdapter.ViewHolder>() {
    private val borrowBoxTask = ObjectBox.boxStore.boxFor(Task::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.text.text = item.name
        holder.idView.isChecked = item.done != null
        holder.contentView.text =
            if (item.deadline == null) "" else "${item.deadline?.day}.${item.deadline?.month + 1}.${item.deadline?.year}"
        holder.idView.setOnClickListener {
            if (holder.idView!!.isChecked)
                item.done = Date()
            else
                item.done = null
            borrowBoxTask.put(item)
        }
        holder.text.setOnClickListener {

            val bundle = Bundle()
            bundle.putLong("id", item.id)

            val intent: Intent = Intent(holder.itemView.context, TaskActivity::class.java)
            intent.putExtra("id", item.id)
            startActivity(holder.itemView.context, intent, bundle)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: CheckBox = binding.checkBoxTask
        val contentView: TextView = binding.itemDateTask
        val text: TextView = binding.checkBoxTextView

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}