package com.example.fourtaxis.database

import com.example.fourtaxis.models.UserModel
import com.google.firebase.auth.FirebaseAuth

lateinit var CURRENT_UID: String
lateinit var USER: UserModel
lateinit var AUTH: FirebaseAuth

const val USERS = "users"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

inline fun initUser(crossinline function: () -> Unit) {

}