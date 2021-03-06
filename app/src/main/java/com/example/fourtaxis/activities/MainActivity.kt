package com.example.fourtaxis.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.fourtaxis.R
import com.example.fourtaxis.database.AUTH
import com.example.fourtaxis.database.initFirebase
import com.example.fourtaxis.database.initUser
import com.example.fourtaxis.fragments.chat.ChatsFragment
import com.example.fourtaxis.fragments.login_register.LoginFragment
import com.example.fourtaxis.fragments.profile.ProfileFragment
import com.example.fourtaxis.fragments.rides.MyRidesFragment
import com.example.fourtaxis.fragments.rides.RidesFragment
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var mToolbar: Toolbar

    var ride = RideModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation_view.setOnNavigationItemSelectedListener(this)

        APP_ACTIVITY = this
        initFirebase()
        initUser {
            initFields()
            initFunc()
        }
    }

    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser != null)
            replaceFragment(RidesFragment(), false)
        else {
            replaceFragment(LoginFragment(), false)
            navigation_view.visibility = View.GONE
        }
    }

    private fun initFields() {
        mToolbar = main_toolbar
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_rides -> replaceFragment(RidesFragment(), false)
            R.id.nav_my_rides -> replaceFragment(MyRidesFragment(), false)
            R.id.nav_profile -> replaceFragment(ProfileFragment(), false)
            R.id.nav_chat -> replaceFragment(ChatsFragment(), false)
        }
        return true
    }
}