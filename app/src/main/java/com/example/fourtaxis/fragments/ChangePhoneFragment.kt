package com.example.fourtaxis.fragments

import android.view.View
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.database.USER
import com.example.fourtaxis.utils.APP_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_change_phone.*

class ChangePhoneFragment : Fragment(R.layout.fragment_change_phone) {

    override fun onStart() {
        super.onStart()

        APP_ACTIVITY.navigation_view.visibility = View.GONE
        et_change_number.setText(USER.phone)
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.navigation_view.visibility = View.VISIBLE
    }

}