package com.example.fourtaxis.fragments.rides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.*
import com.example.fourtaxis.fragments.chat.SingleChatFragment
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.models.UserModel
import com.example.fourtaxis.utils.downloadAndSetImage
import com.example.fourtaxis.utils.replaceFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.ride_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RidesAdapter : RecyclerView.Adapter<RidesAdapter.RideHolder>() {

    private var mListRides = emptyList<RideModel>()

    private var user1 = UserModel()
    private var user2 = UserModel()
    private var user3 = UserModel()
    private var user4 = UserModel()

    class RideHolder(view: View) : RecyclerView.ViewHolder(view) {

        val peopleNeed: TextView = view.tv_rides_people
        val time: TextView = view.tv_rides_time
        val day: TextView = view.tv_rides_day
        val destFrom: TextView = view.tv_rides_dest_from
        val destWhere: TextView = view.tv_rides_dest_where

        val constraintLayoutMain: ConstraintLayout = view.layout_ride_item_main
        val constraintLayoutPeople: LinearLayout = view.layout_ride_people
        val ride_drop_down: ImageView = view.ride_drop_down

        val person1_name: TextView = view.tv_people_user1
        val person1_chat: CircleImageView = view.civ_ride_message_user1
        val person1_photo: CircleImageView = view.civ_people_user1

        val person2_name: TextView = view.tv_people_user_2
        val person2_chat: CircleImageView = view.civ_ride_message_user2
        val person2_exit: CircleImageView = view.civ_ride_exit2
        val person2_photo: CircleImageView = view.civ_people_user_2

        val person3_name: TextView = view.tv_people_user_3
        val person3_chat: CircleImageView = view.civ_ride_message_user3
        val person3_exit: CircleImageView = view.civ_ride_exit3
        val person3_photo: CircleImageView = view.civ_people_user_3

        val person4_name: TextView = view.tv_people_user_4
        val person4_chat: CircleImageView = view.civ_ride_message_user4
        val person4_exit: CircleImageView = view.civ_ride_exit4
        val person4_photo: CircleImageView = view.civ_people_user_4


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHolder {
        return RideHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ride_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RideHolder, position: Int) {

        if (!checkDate(mListRides[position].date)) {
            holder.constraintLayoutMain.visibility = View.GONE
            holder.constraintLayoutMain.layoutParams = RecyclerView.LayoutParams(0, 0)
        } else {
            holder.destFrom.text = mListRides[position].from
            holder.destWhere.text = mListRides[position].where
            holder.time.text = mListRides[position].time
            holder.day.text = setDate(mListRides[position].date)
            holder.peopleNeed.text = "${mListRides[position].people.size}/4"

            holder.ride_drop_down.setOnClickListener {
                rideOnClickListener(holder, position, holder.ride_drop_down)
            }

            holder.person1_chat.setOnClickListener {
                rideOnClickListener(holder, position, holder.person1_chat)
            }
            holder.person2_chat.setOnClickListener {
                rideOnClickListener(holder, position, holder.person2_chat)
            }
            holder.person3_chat.setOnClickListener {
                rideOnClickListener(holder, position, holder.person3_chat)
            }
            holder.person4_chat.setOnClickListener {
                rideOnClickListener(holder, position, holder.person4_chat)
            }
            holder.person2_photo.setOnClickListener {
                rideOnClickListener(holder, position, holder.person2_photo)
            }
            holder.person3_photo.setOnClickListener {
                rideOnClickListener(holder, position, holder.person3_photo)
            }
            holder.person4_photo.setOnClickListener {
                rideOnClickListener(holder, position, holder.person4_photo)
            }
            holder.person2_exit.setOnClickListener {
                rideOnClickListener(holder, position, holder.person2_exit)
            }
        }
    }

    private fun rideOnClickListener(
        holder: RideHolder,
        position: Int,
        layout: Any
    ) {
        when (layout) {
            holder.ride_drop_down ->
                when (holder.constraintLayoutPeople.visibility) {
                    View.VISIBLE -> {
                        holder.constraintLayoutPeople.visibility = View.GONE
                        holder.ride_drop_down.setImageResource(R.drawable.ic_drop_down)
                    }
                    View.GONE -> {
                        holder.ride_drop_down.setImageResource(R.drawable.ic_drop_up)
                        holder.constraintLayoutPeople.visibility = View.VISIBLE
                        updatePeopleInfo(holder, position)
                    }
                }
            holder.person1_chat ->
                if (user1.id.isNotEmpty() && user1.id != CURRENT_UID)
                    replaceFragment(SingleChatFragment(user1))
            holder.person2_exit -> {
                deletePersonFromRide(mListRides[position]) {
                    holder.person2_photo.setImageResource(R.drawable.ic_add)
                    holder.person2_name.text = "Пусто"
                    holder.person2_exit.visibility = View.GONE

                }
            }
            holder.person3_exit -> {
                deletePersonFromRide(mListRides[position]) {
                    holder.person3_photo.setImageResource(R.drawable.ic_add)
                    holder.person3_name.text = "Пусто"
                    holder.person3_exit.visibility = View.GONE
                }
            }
            holder.person4_exit -> {
                deletePersonFromRide(mListRides[position]) {
                    holder.person4_photo.setImageResource(R.drawable.ic_add)
                    holder.person4_name.text = "Пусто"
                    holder.person4_exit.visibility = View.GONE
                }
            }
            holder.person2_photo ->
                if (user2.id.isEmpty() && !mListRides[position].people.contains(CURRENT_UID))
                    addPersonToRide(mListRides[position]) {
                        holder.person2_photo.downloadAndSetImage(USER.photoUrl)
                        holder.person2_name.text = USER.fullName
                        holder.person2_exit.visibility = View.VISIBLE
                    }
            holder.person3_photo ->
                if (user3.id.isEmpty() && !mListRides[position].people.contains(CURRENT_UID))
                    addPersonToRide(mListRides[position]) {
                        holder.person3_photo.downloadAndSetImage(USER.photoUrl)
                        holder.person3_name.text = USER.fullName
                        holder.person3_exit.visibility = View.VISIBLE
                        updatePeopleInfo(holder, position)
                    }
            holder.person4_photo ->
                if (user4.id.isEmpty() && !mListRides[position].people.contains(CURRENT_UID))
                    addPersonToRide(mListRides[position]) {
                        holder.person4_photo.downloadAndSetImage(USER.photoUrl)
                        holder.person4_name.text = USER.fullName
                        holder.person4_exit.visibility = View.VISIBLE
                        updatePeopleInfo(holder, position)
                    }
            holder.person1_chat -> replaceFragment(SingleChatFragment(user1))
            holder.person2_chat -> replaceFragment(SingleChatFragment(user2))
            holder.person3_chat -> replaceFragment(SingleChatFragment(user3))
            holder.person4_chat -> replaceFragment(SingleChatFragment(user4))
        }

    }

    private fun setDate(date: String): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())
        val currentDateText = dateFormat.format(currentDate)

        return when ("${date[0]}${date[1]}".toInt() - "${currentDateText[0]}${currentDateText[1]}".toInt()) {
            0 -> "Сегодня"
            1 -> "Завтра"
            else -> date
        }
    }

    private fun checkDate(date: String): Boolean {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())
        val currentDateText = dateFormat.format(currentDate)

        if ("${date[0]}${date[1]}".toInt() >= "${currentDateText[0]}${currentDateText[1]}".toInt())
            if ("${date[3]}${date[4]}".toInt() >= "${currentDateText[3]}${currentDateText[4]}".toInt())
                return true
        return false
    }

    override fun getItemCount() = mListRides.size

    private fun updatePeopleInfo(
        holder: RideHolder,
        position: Int
    ) {

        holder.person2_name.text = "Пусто"
        holder.person2_photo.setImageResource(R.drawable.ic_add)
        holder.person2_exit.visibility = View.GONE
        holder.person2_chat.visibility = View.GONE
        holder.person3_name.text = "Пусто"
        holder.person3_photo.setImageResource(R.drawable.ic_add)
        holder.person3_exit.visibility = View.GONE
        holder.person3_chat.visibility = View.GONE
        holder.person4_name.text = "Пусто"
        holder.person4_photo.setImageResource(R.drawable.ic_add)
        holder.person4_exit.visibility = View.GONE
        holder.person4_chat.visibility = View.GONE

        FIRESTORE.collection(USERS).document(mListRides[position].creatorID).get()
            .addOnSuccessListener {
                user1 = it.toObject(UserModel::class.java) ?: UserModel()
                holder.person1_name.text = user1.fullName
                holder.person1_photo.downloadAndSetImage(user1.photoUrl)
                if (user1.id != CURRENT_UID)
                    holder.person1_chat.visibility = View.VISIBLE
            }
        if (mListRides[position].people.size > 1) {
            FIRESTORE.collection(USERS).document(mListRides[position].people[1])
                .get()
                .addOnSuccessListener {
                    user2 = it.toObject(UserModel::class.java) ?: UserModel()
                    holder.person2_name.text = user2.fullName
                    holder.person2_photo.downloadAndSetImage(user2.photoUrl)
                    if (user2.id == CURRENT_UID)
                        holder.person2_exit.visibility = View.VISIBLE
                    else holder.person2_chat.visibility = View.VISIBLE
                }
        }

        if (mListRides[position].people.size > 2) {
            FIRESTORE.collection(USERS).document(mListRides[position].people[2])
                .get()
                .addOnSuccessListener {
                    user3 = it.toObject(UserModel::class.java) ?: UserModel()
                    holder.person3_name.text = user3.fullName
                    holder.person3_photo.downloadAndSetImage(user3.photoUrl)
                    if (user3.id == CURRENT_UID)
                        holder.person3_exit.visibility = View.VISIBLE
                    else holder.person3_chat.visibility = View.VISIBLE
                }
        }

        if (mListRides[position].people.size > 3) {
            FIRESTORE.collection(USERS).document(mListRides[position].people[3])
                .get()
                .addOnSuccessListener {
                    user4 = it.toObject(UserModel::class.java) ?: UserModel()
                    holder.person4_name.text = user4.fullName
                    holder.person4_photo.downloadAndSetImage(user4.photoUrl)
                    if (user4.id == CURRENT_UID)
                        holder.person4_exit.visibility = View.VISIBLE
                    else holder.person4_chat.visibility = View.VISIBLE
                }
        }
    }

    fun setList(list: List<RideModel>) {
        mListRides = list
        notifyDataSetChanged()
    }
}