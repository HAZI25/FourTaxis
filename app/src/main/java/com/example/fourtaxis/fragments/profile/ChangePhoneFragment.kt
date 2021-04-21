package com.example.fourtaxis.fragments.profile

import com.example.fourtaxis.R
import com.example.fourtaxis.database.*
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.showToast
import kotlinx.android.synthetic.main.fragment_change_phone.*

class ChangePhoneFragment : BaseChangeFragment(R.layout.fragment_change_phone) {

    override fun onStart() {
        super.onStart()

        et_change_number.setText(USER.phone)

        fab_change_phone.setOnClickListener {
            val hashMap = hashMapOf<String, Any>()
            val number = et_change_number.text.toString()
            hashMap[PHONE] = number
            FIRESTORE.collection(USERS).document(CURRENT_UID).update(hashMap)
                .addOnCompleteListener {
                if (it.isSuccessful) {
                    USER.phone = number
                    APP_ACTIVITY.supportFragmentManager.popBackStack()
                } else showToast(it.exception?.message.toString())
            }
        }
    }
}