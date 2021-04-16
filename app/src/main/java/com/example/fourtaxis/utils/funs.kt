package com.example.fourtaxis.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R

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