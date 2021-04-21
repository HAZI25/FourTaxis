package com.example.fourtaxis.fragments.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.fourtaxis.R
import com.example.fourtaxis.database.*
import com.example.fourtaxis.utils.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        APP_ACTIVITY.main_toolbar.title = "Profile"
        initFields()
    }

    private fun initFields() {
        tv_profile_username.text = USER.fullName
        tv_profile_email.text = USER.email
        tv_profile_phone.text = USER.phone
        tv_profile_bio.text = USER.bio
        profile_user_photo.downloadAndSetImage(USER.photoUrl)

        profile_user_photo.setOnClickListener { changePhotoUser() }

        cardView_profile_phone.setOnClickListener {
            replaceFragment(ChangePhoneFragment())
        }
        cardView_profile_bio.setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE.child(FOLDER_PROFILE_IMAGE).child(CURRENT_UID)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        profile_user_photo.downloadAndSetImage(it)
                        USER.photoUrl = it
                        showToast("Update")
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        APP_ACTIVITY.menuInflater.inflate(R.menu.profile_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.profile_menu_exit) {
            AUTH.signOut()
            restartActivity()
        }
        return super.onOptionsItemSelected(item)
    }
}