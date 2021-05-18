package com.example.fourtaxis.fragments.login_register

import android.view.View
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.database.AUTH
import com.example.fourtaxis.database.FIRESTORE
import com.example.fourtaxis.database.USER
import com.example.fourtaxis.database.USERS
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.restartActivity
import com.example.fourtaxis.utils.showToast
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register), View.OnClickListener {
    override fun onStart() {
        super.onStart()

        btn_register.setOnClickListener(this)
        tv_login.setOnClickListener(this)
        APP_ACTIVITY.mToolbar.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        if (view != null)
            when (view.id) {
                R.id.tv_login -> APP_ACTIVITY.onBackPressed()

                R.id.btn_register -> {
                    val email = et_email.text.toString()
                    val password = et_password.text.toString()

                    AUTH.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {

                            USER.id = AUTH.currentUser?.uid ?: ""
                            USER.fullname = et_fullName.text.toString()
                            USER.phone = et_phoneNumber.text.toString()
                            USER.email = email

                            FIRESTORE.collection(USERS).document(USER.id)
                                .set(USER, SetOptions.merge())
                                .addOnCompleteListener {
                                    if (it.isSuccessful)
                                        restartActivity()
                                    else showToast(it.exception?.message.toString())
                                }
                        } else showToast("Registration Failed. ${it.exception?.message.toString()}")
                    }
                }
            }
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.mToolbar.visibility = View.VISIBLE
    }
}