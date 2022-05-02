package ru.kalianova.rpgtracker

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

import ru.kalianova.rpgtracker.placeholder.PlaceholderContent.PlaceholderItem
import ru.kalianova.rpgtracker.databinding.FragmentTaskItemBinding
import ru.kalianova.rpgtracker.model.Task

class TaskItemRecyclerViewAdapter(
    private val values: List<Task>
) : RecyclerView.Adapter<TaskItemRecyclerViewAdapter.ViewHolder>() {

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
        holder.idView.text = item.name
        holder.idView.isChecked = item.done != null
        holder.contentView.text = item.date.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: CheckBox = binding.checkBoxTask
        val contentView: TextView = binding.itemDateTask

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}