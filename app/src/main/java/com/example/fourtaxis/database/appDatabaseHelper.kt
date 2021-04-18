package com.example.fourtaxis.database

import com.example.fourtaxis.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

lateinit var CURRENT_UID: String
lateinit var USER: UserModel
lateinit var AUTH: FirebaseAuth
lateinit var FIRESTORE: FirebaseFirestore

const val USERS = "users"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    FIRESTORE = FirebaseFirestore.getInstance()
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

inline fun initUser(crossinline function: () -> Unit) {
    FIRESTORE.collection(USERS).document(CURRENT_UID).get().addOnSuccessListener {
        USER = it.toObject(UserModel::class.java) ?: UserModel()
    }
    function()
}