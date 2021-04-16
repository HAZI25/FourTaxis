package com.example.fourtaxis.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.fourtaxis.R
import com.example.fourtaxis.database.AUTH
import com.example.fourtaxis.database.initFirebase
import com.example.fourtaxis.fragments.LoginFragment
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this
        initFirebase()
        initFields()
        initFunc()
    }



    private fun initFunc() {
        setSupportActionBar(mToolbar)
        if (AUTH.currentUser == null) {
            replaceFragment(LoginFragment(), false)
            navigation_view.visibility = View.GONE
        }
    }

    private fun initFields() {
        mToolbar = main_toolbar
    }
}