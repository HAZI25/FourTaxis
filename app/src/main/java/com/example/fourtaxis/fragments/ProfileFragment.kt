package com.example.fourtaxis.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.database.AUTH
import com.example.fourtaxis.database.USER
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import com.example.fourtaxis.utils.restartActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        APP_ACTIVITY.main_toolbar.title = "Profile"
        initFields()
    }

    private fun initFields() {
        tv_profile_username.text = USER.fullName
        tv_profile_email.text = USER.email
        tv_profile_phone.text = USER.phone

        cardView_profile_phone.setOnClickListener {
            replaceFragment(ChangePhoneFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        APP_ACTIVITY.menuInflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.profile_menu_exit) {
            AUTH.signOut()
            restartActivity()
        }
        return super.onOptionsItemSelected(item)
    }
}