package com.example.fourtaxis.fragments.chat

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.CHATS
import com.example.fourtaxis.database.CHATS_INTERLOCUTOR
import com.example.fourtaxis.database.CURRENT_UID
import com.example.fourtaxis.database.FIRESTORE
import com.example.fourtaxis.models.ChatModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import kotlinx.android.synthetic.main.fragment_chats.*

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ChatAdapter
    private var mListChats = emptyList<ChatModel>()

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mToolbar.title = "Контакты"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = chats_contacts_recycler_view
        mAdapter = ChatAdapter()
        mRecyclerView.adapter = mAdapter

        FIRESTORE.collection(CHATS).document(CURRENT_UID).collection(CHATS_INTERLOCUTOR)
            .addSnapshotListener { value, error ->
                value?.let {
                    mListChats = it.toObjects(ChatModel::class.java)
                    mAdapter.setList(mListChats)
                }
            }
    }
}