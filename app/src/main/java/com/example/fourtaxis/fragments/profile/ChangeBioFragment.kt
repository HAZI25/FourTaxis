package com.example.fourtaxis.fragments.profile

import com.example.fourtaxis.R
import com.example.fourtaxis.database.*
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.showToast
import kotlinx.android.synthetic.main.fragment_change_bio.*


class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {
    override fun onStart() {
        super.onStart()

        et_change_bio.setText(USER.bio)

        fab_change_bio.setOnClickListener {
            val hashMap = hashMapOf<String, Any>()
            val bio = et_change_bio.text.toString()
            hashMap[BIO] = bio
            FIRESTORE.collection(USERS).document(CURRENT_UID).update(hashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        USER.bio = bio
                        APP_ACTIVITY.supportFragmentManager.popBackStack()
                    } else showToast(it.exception?.message.toString())
                }
        }
    }
}