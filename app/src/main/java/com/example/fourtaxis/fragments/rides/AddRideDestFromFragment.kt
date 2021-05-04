package com.example.fourtaxis.fragments.rides

import android.widget.ArrayAdapter
import com.example.fourtaxis.R
import com.example.fourtaxis.database.CURRENT_UID
import com.example.fourtaxis.fragments.profile.BaseChangeFragment
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_add_ride_dest_from.*


class AddRideDestFromFragment : BaseChangeFragment(R.layout.fragment_add_ride_dest_from) {
    override fun onStart() {
        super.onStart()

        spinner_rides_from.adapter = ArrayAdapter(
            APP_ACTIVITY,
            R.layout.spinner_item,
            resources.getStringArray(R.array.rides_dest)
        )

        APP_ACTIVITY.ride = RideModel()

        fab_next_ride_dest_from.setOnClickListener {
            APP_ACTIVITY.ride.creatorID = CURRENT_UID
            APP_ACTIVITY.ride.people.add(CURRENT_UID)
            APP_ACTIVITY.ride.from = spinner_rides_from.selectedItem.toString()
            replaceFragment(AddRideDestWhereFragment())
        }
    }
}