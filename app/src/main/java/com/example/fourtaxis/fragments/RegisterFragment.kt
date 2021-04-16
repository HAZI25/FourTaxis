package com.example.fourtaxis.fragments

import android.view.View
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.database.AUTH
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.showToast
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register), View.OnClickListener {
    override fun onStart() {
        super.onStart()

        btn_register.setOnClickListener(this)
        tv_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null)
            when (view.id) {
                R.id.tv_login -> APP_ACTIVITY.onBackPressed()
                R.id.btn_register -> {
                    val email = et_email.text.toString()
                    val password = et_password.text.toString()

                    AUTH.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful)
                            showToast("Success")
                        else showToast("Registration Failed. ${it.exception?.message.toString()}")
                    }
                }
            }
    }
}