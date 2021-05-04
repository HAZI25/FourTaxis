package com.example.fourtaxis.fragments.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.CURRENT_UID
import com.example.fourtaxis.models.MessageModel
import com.example.fourtaxis.utils.asTime
import kotlinx.android.synthetic.main.message_item.view.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var mListMessages = emptyList<MessageModel>()

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blockUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMessage: TextView = view.chat_user_message
        val chatUserMessageTime: TextView = view.chat_user_message_time

        val blockReceivedMessage: ConstraintLayout = view.block_received_message
        val chatReceivedMessage: TextView = view.chat_received_message
        val chatReceivedMessageTime: TextView = view.chat_received_message_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        return SingleChatHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.message_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mListMessages[position].from == CURRENT_UID) {
            holder.blockUserMessage.visibility = View.VISIBLE
            holder.blockReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessages[position].text

            val ts = mListMessages[position].timeStamp
            if (ts != null)
                holder.chatUserMessageTime.text = ts.toString().asTime()
        } else {
            holder.blockUserMessage.visibility = View.GONE
            holder.blockReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mListMessages[position].text

            val ts = mListMessages[position].timeStamp
            if (ts != null)
                holder.chatReceivedMessageTime.text = ts.toString().asTime()
        }
    }

    override fun getItemCount(): Int = mListMessages.size

    fun setList(list: List<MessageModel>) {
        mListMessages = list
        notifyDataSetChanged()
    }
}


