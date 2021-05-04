package com.example.fourtaxis.fragments.rides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.fourtaxis.utils.showToast
import com.google.firebase.firestore.FieldValue
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.rides_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RidesAdapter : RecyclerView.Adapter<RidesAdapter.RideHolder>() {

    private var mListRides = emptyList<RideModel>()

    private var user1 = UserModel()
    private var user2 = UserModel()
    private var user3 = UserModel()
    private var user4 = UserModel()

    class RideHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: ConstraintLayout = view.constraint_layout_ride_dest
        val peopleNeed: TextView = view.tv_rides_people
        val time: TextView = view.tv_rides_time
        val day: TextView = view.tv_rides_day
        val destFrom: TextView = view.tv_rides_dest_from
        val destWhere: TextView = view.tv_rides_dest_where

        val constraintLayoutPeople: LinearLayout = view.layout_ride_people

        val person1_name: TextView = view.tv_people_user_1
        val person1_photo: CircleImageView = view.civ_people_user_1

        val layout_user_2: LinearLayout = view.layout_user_2
        val person2_name: TextView = view.tv_people_user_2
        val person2_photo: CircleImageView = view.civ_people_user_2

        val layout_user_3: LinearLayout = view.layout_user_3
        val person3_name: TextView = view.tv_people_user_3
        val person3_photo: CircleImageView = view.civ_people_user_3

        val layout_user_4: LinearLayout = view.layout_user_4
        val person4_name: TextView = view.tv_people_user_4
        val person4_photo: CircleImageView = view.civ_people_user_4


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideHolder {
        return RideHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rides_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RideHolder, position: Int) {
        holder.destFrom.text = mListRides[position].from
        holder.destWhere.text = mListRides[position].where
        holder.time.text = mListRides[position].time
        holder.day.text = setDate(mListRides[position].date)
        holder.peopleNeed.text = "${mListRides[position].people.size}/4"

        holder.layout.setOnClickListener {


            when (holder.constraintLayoutPeople.visibility) {
                View.VISIBLE -> holder.constraintLayoutPeople.visibility = View.GONE
                View.GONE -> {
                    holder.constraintLayoutPeople.visibility = View.VISIBLE
                    FIRESTORE.collection(USERS).document(mListRides[position].creatorID).get()
                        .addOnSuccessListener {
                            user1 = it.toObject(UserModel::class.java) ?: UserModel()
                            holder.person1_name.text = user1.fullName
                            holder.person1_photo.downloadAndSetImage(user1.photoUrl)
                        }
                    if (mListRides[position].people.size > 1) {
                        FIRESTORE.collection(USERS).document(mListRides[position].people[1]).get()
                            .addOnSuccessListener {
                                user2 = it.toObject(UserModel::class.java) ?: UserModel()
                                holder.person2_name.text = user2.fullName
                                holder.person2_photo.downloadAndSetImage(user2.photoUrl)
                            }
                    }

                    if (mListRides[position].people.size > 2) {
                        FIRESTORE.collection(USERS).document(mListRides[position].people[2]).get()
                            .addOnSuccessListener {
                                user3 = it.toObject(UserModel::class.java) ?: UserModel()
                                holder.person3_name.text = user3.fullName
                                holder.person3_photo.downloadAndSetImage(user3.photoUrl)
                            }
                    }

                    if (mListRides[position].people.size > 3) {
                        FIRESTORE.collection(USERS).document(mListRides[position].people[3]).get()
                            .addOnSuccessListener {
                                user4 = it.toObject(UserModel::class.java) ?: UserModel()
                                holder.person4_name.text = user4.fullName
                                holder.person4_photo.downloadAndSetImage(user4.photoUrl)
                            }
                    }

                }
            }
        }

        holder.layout_user_2.setOnClickListener {
            when {
                user2.id.isNotEmpty() && user2.id != CURRENT_UID -> {
                    replaceFragment(SingleChatFragment(user2))
                }
                user2.id.isEmpty() && mListRides[position].people.contains(CURRENT_UID) -> showToast("Вы уже присоединились к поездке")
                else -> FIRESTORE.collection(RIDES).document(mListRides[position].creatorID)
                    .update("people", FieldValue.arrayUnion(CURRENT_UID)).addOnSuccessListener {
                        holder.person2_photo.downloadAndSetImage(USER.photoUrl)
                        holder.person2_name.text = USER.fullName
                    }
            }
        }

        holder.layout_user_3.setOnClickListener {
            when {
                user3.id.isNotEmpty() && user3.id != CURRENT_UID -> {
                    replaceFragment(SingleChatFragment(user3))
                }
                user3.id.isEmpty() && mListRides[position].people.contains(CURRENT_UID) -> showToast("Вы уже присоединились к поездке")
                else -> FIRESTORE.collection(RIDES).document(mListRides[position].creatorID)
                    .update("people", FieldValue.arrayUnion(CURRENT_UID)).addOnSuccessListener {
                        holder.person3_photo.downloadAndSetImage(USER.photoUrl)
                        holder.person3_name.text = USER.fullName
                    }
            }
        }

        holder.layout_user_4.setOnClickListener {
            when {
                user4.id.isNotEmpty() && user4.id != CURRENT_UID -> {
                    replaceFragment(SingleChatFragment(user4))
                }
                user4.id.isEmpty() && mListRides[position].people.contains(CURRENT_UID) -> showToast("Вы уже присоединились к поездке")
                else -> FIRESTORE.collection(RIDES).document(mListRides[position].creatorID)
                    .update("people", FieldValue.arrayUnion(CURRENT_UID)).addOnSuccessListener {
                        holder.person4_photo.downloadAndSetImage(USER.photoUrl)
                        holder.person4_name.text = USER.fullName
                    }
            }
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

    override fun getItemCount() = mListRides.size

    fun setList(list: List<RideModel>) {
        mListRides = list
        notifyDataSetChanged()
    }
}