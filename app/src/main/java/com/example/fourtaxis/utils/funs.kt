package com.example.fourtaxis.utils

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.activities.MainActivity

fun replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    val fm = APP_ACTIVITY.supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, fragment)
    if (addStack)
        fm.addToBackStack(null)
    fm.commit()
}

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun restartActivity() {
    val intent = Intent(APP_ACTIVITY, MainActivity::class.java)
    APP_ACTIVITY.startActivity(intent)
    APP_ACTIVITY.finish()
}