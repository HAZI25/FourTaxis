package com.example.fourtaxis.fragments.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.CURRENT_UID
import com.example.fourtaxis.database.FIRESTORE
import com.example.fourtaxis.database.MESSAGES
import com.example.fourtaxis.database.USERS
import com.example.fourtaxis.models.ChatModel
import com.example.fourtaxis.models.MessageModel
import com.example.fourtaxis.models.UserModel
import com.example.fourtaxis.utils.downloadAndSetImage
import com.example.fourtaxis.utils.replaceFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.chat_item.view.*

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    private var mListChats = emptyList<ChatModel>()

    class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {

        val chatLayout: FrameLayout = view.contact_chat_layout
        val userPhoto: CircleImageView = view.civ_contact_chat_photo
        val userFullname: TextView = view.tv_contact_chat_fullname
        val lastMessage: TextView = view.tv_contact_chat_last_message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.chat_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        FIRESTORE.collection(USERS).document(mListChats[position].id).get().addOnSuccessListener {
            val user = it.toObject(UserModel::class.java) ?: UserModel()
            holder.userPhoto.downloadAndSetImage(user.photoUrl)
            holder.userFullname.text = user.fullname
            FIRESTORE.collection(MESSAGES).document(CURRENT_UID).collection(mListChats[position].id)
                .orderBy("timeStamp")
                .addSnapshotListener { value, error ->
                    value?.let {
                        val message = it.toObjects(MessageModel::class.java).last()
                        holder.lastMessage.text = message.text
                    }
                }
            holder.chatLayout.setOnClickListener {
                replaceFragment(SingleChatFragment(user))
            }
        }
    }

    override fun getItemCount(): Int = mListChats.size

    fun setList(list: List<ChatModel>) {
        mListChats = list
        notifyDataSetChanged()
    }
}