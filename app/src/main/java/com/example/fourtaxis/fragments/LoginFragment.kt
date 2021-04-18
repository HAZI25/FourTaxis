package com.example.fourtaxis.fragments

import android.view.View
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.database.AUTH
import com.example.fourtaxis.utils.replaceFragment
import com.example.fourtaxis.utils.restartActivity
import com.example.fourtaxis.utils.showToast
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login), View.OnClickListener {
    override fun onStart() {
        super.onStart()

        tv_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null)
            when (view.id) {
                R.id.tv_register -> replaceFragment(RegisterFragment())
                R.id.btn_login -> {

                    val email = et_email.text.toString()
                    val password = et_password.text.toString()

                    AUTH.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) restartActivity()
                        else showToast("Fail ${it.exception?.message.toString()}")
                    }
                }
            }
    }
}