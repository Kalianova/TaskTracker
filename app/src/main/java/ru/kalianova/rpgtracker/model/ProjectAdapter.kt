package ru.kalianova.rpgtracker.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.TextView
import ru.kalianova.rpgtracker.R
import ru.kalianova.rpgtracker.model.TaskType.TaskType

class ProjectAdapter(context: Context, list: List<Project>)
    : ArrayAdapter<Project>(context, 0, list){
    private var layoutInflater = LayoutInflater.from(context)

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = layoutInflater.inflate(R.layout.spinner_item, null, true)
        return view(view, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        if (cv == null)
            cv = layoutInflater.inflate(R.layout.spinner_item, parent, false)
        return view(cv!!, position)
    }

    private fun view(view: View, position: Int): View {

        val item: Project = getItem(position) ?: return view
        val colorName = view.findViewById<TextView>(R.id.text)

        colorName.text = item.name
        return view

    }

    override fun getFilter(): Filter {
        return filter
    }
    private var filter: Filter = object : Filter(){
        override fun convertResultToString(resultValue: Any?): CharSequence {
            val str = (resultValue as Project).name
            return str
        }
        override fun performFiltering(p0: CharSequence?): FilterResults {
            TODO("Not yet implemented")
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            TODO("Not yet implemented")
        }

    }
}