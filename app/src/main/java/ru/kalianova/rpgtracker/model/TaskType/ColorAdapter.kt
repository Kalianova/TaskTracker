package ru.kalianova.rpgtracker.model.TaskType

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.ColorInt
import ru.kalianova.rpgtracker.R

class ColorAdapter(context: Context, list: List<ColorList>) :
    ArrayAdapter<ColorList>(context, 0, list) {
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

        val item: ColorList = getItem(position) ?: return view
        val colorName = view.findViewById<TextView>(R.id.textColor)
        val colorImage = view.findViewById<View>(R.id.colorBlob)

        colorName.text = item.name
        colorImage.setBackgroundColor(Color.parseColor(item.code))
        return view

    }



    override fun getFilter(): Filter {
        return filter
    }
    private var filter: Filter = object : Filter(){
        override fun convertResultToString(resultValue: Any?): CharSequence {
            val str = (resultValue as ColorList).name
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