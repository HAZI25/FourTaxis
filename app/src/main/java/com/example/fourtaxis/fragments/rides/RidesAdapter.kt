package com.example.fourtaxis.fragments.rides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.models.RideModel
import kotlinx.android.synthetic.main.rides_item.view.*

class RidesAdapter : RecyclerView.Adapter<RidesAdapter.RideHolder>() {

    private var mListRides = emptyList<RideModel>()

    class RideHolder(view: View) : RecyclerView.ViewHolder(view) {
        val people: TextView = view.tv_rides_people
        val time: TextView = view.tv_rides_time
        val day: TextView = view.tv_rides_day
        val destFrom: TextView = view.tv_rides_dest_from
        val destWhere: TextView = view.tv_rides_dest_where
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHolder {
        return RideHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rides_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RideHolder, position: Int) {
        holder.destFrom.text = mListRides[position].from
        holder.destWhere.text = mListRides[position].where
        holder.time.text = mListRides[position].time
    }

    override fun getItemCount() = mListRides.size

    fun setList(list: List<RideModel>) {
        mListRides = list
        notifyDataSetChanged()
    }
}