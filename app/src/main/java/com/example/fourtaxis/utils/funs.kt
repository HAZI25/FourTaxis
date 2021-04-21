package com.example.fourtaxis.utils

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.activities.MainActivity
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

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

fun CircleImageView.downloadAndSetImage(url: String) {
    Picasso.get().load(url).fit().placeholder(R.drawable.photo_placeholder).into(this)
}

fun checkDateTimeDigit(digit: Int): String {
    if (digit < 10) return "0$digit"
    return digit.toString()
}