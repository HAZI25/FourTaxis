package com.example.fourtaxis.fragments.rides

import android.widget.ArrayAdapter
import com.example.fourtaxis.R
import com.example.fourtaxis.fragments.profile.BaseChangeFragment
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_add_ride_dest_where.*


class AddRideDestWhereFragment : BaseChangeFragment(R.layout.fragment_add_ride_dest_where) {

    override fun onStart() {
        super.onStart()

        spinner_rides_where.adapter = ArrayAdapter(
            APP_ACTIVITY,
            R.layout.spinner_item,
            resources.getStringArray(R.array.rides_dest)
        )

        fab_next_ride_dest_where.setOnClickListener {
            APP_ACTIVITY.ride.where = spinner_rides_where.selectedItem.toString()
            replaceFragment(AddRideDateFragment()) }
    }
}