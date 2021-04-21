package com.example.fourtaxis.fragments.profile

import android.view.View
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.utils.APP_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.*

open class BaseChangeFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.navigation_view.visibility = View.GONE

        APP_ACTIVITY.mToolbar.setNavigationIcon(R.drawable.ic_back)
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener { APP_ACTIVITY.supportFragmentManager.popBackStack() }
    }

    override fun onPause() {
        super.onPause()
        APP_ACTIVITY.navigation_view.visibility = View.VISIBLE
        APP_ACTIVITY.mToolbar.navigationIcon = null
    }
}