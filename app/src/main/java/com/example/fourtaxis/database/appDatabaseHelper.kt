package com.example.fourtaxis.database

import android.net.Uri
import com.example.fourtaxis.models.ChatModel
import com.example.fourtaxis.models.MessageModel
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.models.UserModel
import com.example.fourtaxis.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

lateinit var CURRENT_UID: String
lateinit var USER: UserModel
lateinit var AUTH: FirebaseAuth
lateinit var FIRESTORE: FirebaseFirestore
lateinit var REF_STORAGE: StorageReference

const val MESSAGES = "messages"
const val USERS = "users"
const val RIDES = "rides"
const val CHATS = "chats_list"
const val CHATS_INTERLOCUTOR = "interlocutor"

const val PHONE = "phone"
const val BIO = "bio"
const val FOLDER_PROFILE_IMAGE = "folder_profile_image"
const val PHOTO_URL = "photoUrl"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    FIRESTORE = FirebaseFirestore.getInstance()
    REF_STORAGE = FirebaseStorage.getInstance().reference
    USER = UserModel()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
}

inline fun initUser(crossinline function: () -> Unit) {
    FIRESTORE.collection(USERS).document(CURRENT_UID).get().addOnSuccessListener {
        USER = it.toObject(UserModel::class.java) ?: UserModel()
    }
    function()
}

inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    FIRESTORE.collection(USERS).document(CURRENT_UID).update(mapOf(PHOTO_URL to url))
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

fun createRide(ride: RideModel, function: () -> Unit) {
    FIRESTORE.collection(RIDES).document(CURRENT_UID).set(ride, SetOptions.merge())
        .addOnCompleteListener {
            if (it.isSuccessful)
                showToast("Done")
            else
                showToast(it.exception?.message.toString())
            function()
        }
}

fun sendMessage(message: String, received_id: String, function: () -> Unit) {
    val messageKey = FIRESTORE.collection(MESSAGES).document().id

    val messageModel = MessageModel()
    messageModel.from = CURRENT_UID
    messageModel.text = message
    messageModel.timeStamp = FieldValue.serverTimestamp()

    FIRESTORE.collection(MESSAGES)
        .document(CURRENT_UID)
        .collection(received_id)
        .document(messageKey)
        .set(messageModel).addOnSuccessListener {
            FIRESTORE.collection(MESSAGES).document(received_id).collection(CURRENT_UID)
                .document(messageKey).set(messageModel).addOnSuccessListener { function() }
        }
}

fun saveToChatList(id: String) {
    FIRESTORE.collection(CHATS).document(CURRENT_UID).collection(CHATS_INTERLOCUTOR).document(id)
        .set(ChatModel(id)).addOnSuccessListener {
            FIRESTORE.collection(CHATS).document(id).collection(CHATS_INTERLOCUTOR).document(CURRENT_UID)
                .set(ChatModel(CURRENT_UID))
        }
}