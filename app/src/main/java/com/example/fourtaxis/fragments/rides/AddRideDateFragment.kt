package com.example.fourtaxis.fragments.rides

import com.example.fourtaxis.R
import com.example.fourtaxis.database.createRide
import com.example.fourtaxis.fragments.profile.BaseChangeFragment
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.checkDateTimeDigit
import com.example.fourtaxis.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_add_ride_date.*


class AddRideDateFragment : BaseChangeFragment(R.layout.fragment_add_ride_date) {
    override fun onStart() {
        super.onStart()

        fab_create_ride.setOnClickListener {
            APP_ACTIVITY.ride.date = checkDateTimeDigit(datePicker.dayOfMonth) + '.' + checkDateTimeDigit(datePicker.month + 1) + '.' + datePicker.year.toString()
            APP_ACTIVITY.ride.time = checkDateTimeDigit(timePicker.hour) + ':' + checkDateTimeDigit(timePicker.minute)
            createRide(APP_ACTIVITY.ride) {
                replaceFragment(RidesFragment())
            }
        }
    }
}