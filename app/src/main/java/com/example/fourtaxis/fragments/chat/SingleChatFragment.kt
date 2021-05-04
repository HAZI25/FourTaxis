package com.example.fourtaxis.fragments.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.*
import com.example.fourtaxis.fragments.profile.BaseChangeFragment
import com.example.fourtaxis.models.MessageModel
import com.example.fourtaxis.models.UserModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.downloadAndSetImage
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_chat.*
import kotlinx.android.synthetic.main.toolbar_info.view.*

class SingleChatFragment(private val user: UserModel) :
    BaseChangeFragment(R.layout.fragment_single_chat) {

    private lateinit var mToolbarInfo: View
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mMessageListener: ListenerRegistration

    private var mListMessages = emptyList<MessageModel>()

    override fun onStart() {
        super.onStart()
        initToolbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = chat_recycler_view
        mAdapter = SingleChatAdapter()
        mRecyclerView.adapter = mAdapter

        mMessageListener = FIRESTORE.collection(MESSAGES).document(CURRENT_UID).collection(user.id).orderBy("timeStamp")
            .addSnapshotListener { value, error ->
                value?.let {
                    mListMessages = it.toObjects(MessageModel::class.java)
                    mAdapter.setList(mListMessages)
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
                }
            }
    }

    private fun initToolbar() {
        mToolbarInfo = APP_ACTIVITY.mToolbar.toolbar_chat_info
        mToolbarInfo.visibility = View.VISIBLE
        mToolbarInfo.toolbar_chat_image.downloadAndSetImage(user.photoUrl)
        mToolbarInfo.toolbar_chat_fullname.text = user.fullName

        chat_btn_send_message.setOnClickListener {
            val message = chat_input_message.text.toString()
            if (message.isNotEmpty())
                sendMessage(message, user.id) {
                    saveToChatList(user.id)
                    chat_input_message.setText("")
                }
        }
    }



    override fun onPause() {
        super.onPause()
        mMessageListener.remove()
        mToolbarInfo.visibility = View.GONE
    }
}