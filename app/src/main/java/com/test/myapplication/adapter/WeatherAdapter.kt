package com.test.myapplication.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.myapplication.R
import com.test.myapplication.model.DailyModel
import com.test.myapplication.viewmodel.ItemListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherAdapter(private var datas: ArrayList<DailyModel> ) :
RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    lateinit var itemListener: ItemListener
    constructor(item: ItemListener) : this (ArrayList() ){
        itemListener = item
    }

    lateinit var context : Context

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: View
        val txtDia: TextView
        val txtClima: TextView

        init {
            // Define click listener for the ViewHolder's View.
            rootView  = view.findViewById(R.id.rootView)
            txtDia = view.findViewById(R.id.txtDia)
            txtClima= view.findViewById(R.id.txtClima)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.weather_item_layout, viewGroup, false)

        context = viewGroup.context

        return ViewHolder(view)
    }


    fun updateData(newData: ArrayList<DailyModel>){
        datas.clear()
        datas.addAll(newData)
        notifyDataSetChanged()
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val data:DailyModel  = datas.get(position)
        val weather = data.weather.get(0)
        val date = Date(data.dt).toString()
        viewHolder.txtDia.text =context.getString(R.string.dia, date )
        viewHolder.txtClima.text =  context.getString(R.string.clima, weather.description)
        viewHolder.rootView.setOnClickListener{
            itemListener.onItemClick(data)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = datas.size

}
