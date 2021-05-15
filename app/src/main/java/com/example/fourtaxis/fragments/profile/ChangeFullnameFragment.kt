package com.example.fourtaxis.fragments.profile

import com.example.fourtaxis.R
import com.example.fourtaxis.database.*
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.showToast
import kotlinx.android.synthetic.main.fragment_change_fullname.*

class ChangeFullnameFragment : BaseChangeFragment(R.layout.fragment_change_fullname) {
    override fun onStart() {
        super.onStart()

        et_change_fullname.setText(USER.fullName)
        fab_change_fullname.setOnClickListener {
            val hashMap = hashMapOf<String, Any>()
            val fullname = et_change_fullname.text.toString()
            hashMap[FULLNAME] = fullname
            FIRESTORE.collection(USERS).document(CURRENT_UID).update(hashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        USER.fullName = fullname
                        APP_ACTIVITY.supportFragmentManager.popBackStack()
                    } else showToast(it.exception?.message.toString())
                }
        }
    }
}