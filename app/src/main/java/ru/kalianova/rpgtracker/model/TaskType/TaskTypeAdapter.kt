package ru.kalianova.rpgtracker.model.TaskType

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import ru.kalianova.rpgtracker.R
import ru.kalianova.rpgtracker.model.TaskType.TaskType

class TaskTypeAdapter(context: Context, list: List<TaskType>)
    : ArrayAdapter<TaskType>(context, 0, list){
        private var layoutInflater = LayoutInflater.from(context)

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = layoutInflater.inflate(R.layout.color_spinner_item, null, true)
        return view(view, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        if (cv == null)
            cv = layoutInflater.inflate(R.layout.color_spinner_item, parent, false)
        return view(cv!!, position)
    }

    private fun view(view: View, position: Int): View {

        val item: TaskType = getItem(position) ?: return view
        val colorName = view.findViewById<TextView>(R.id.textColor)
        val colorImage = view.findViewById<View>(R.id.colorBlob)

        colorName.text = item.name
        colorImage.setBackgroundColor(Color.parseColor(item.color))
        return view

    }
}