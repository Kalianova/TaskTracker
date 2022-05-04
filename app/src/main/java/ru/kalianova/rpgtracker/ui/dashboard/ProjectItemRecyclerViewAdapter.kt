package ru.kalianova.rpgtracker.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.kalianova.rpgtracker.ListProject
import ru.kalianova.rpgtracker.R
import ru.kalianova.rpgtracker.TaskActivity
import ru.kalianova.rpgtracker.databinding.FragmentProjectItemBinding
import ru.kalianova.rpgtracker.model.Project

import ru.kalianova.rpgtracker.ui.dashboard.placeholder.PlaceholderContent.PlaceholderItem
import ru.kalianova.rpgtracker.ui.home.TaskItemFragment


class ProjectItemRecyclerViewAdapter(
    private val values: List<Project>
) : RecyclerView.Adapter<ProjectItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentProjectItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
        holder.contentView.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("id", item.id)

            val intent: Intent = Intent(holder.itemView.context, ListProject::class.java)
            intent.putExtra("id", item.id)
            ContextCompat.startActivity(holder.itemView.context, intent, bundle)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.projectName

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}