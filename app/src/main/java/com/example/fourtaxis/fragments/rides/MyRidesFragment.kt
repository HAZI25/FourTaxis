package com.example.fourtaxis.fragments.rides

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.FIRESTORE
import com.example.fourtaxis.database.RIDES
import com.example.fourtaxis.database.USER
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_my_rides.*


class MyRidesFragment : Fragment(R.layout.fragment_my_rides) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RidesAdapter
    private lateinit var mRidesListener: ListenerRegistration
    private var mListRides = emptyList<RideModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mToolbar.title = "Мои Поездки"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = my_ride_recycler_view
        mAdapter = RidesAdapter()
        mRecyclerView.adapter = mAdapter

        mRidesListener = FIRESTORE.collection(RIDES)
            .whereEqualTo("creatorID", USER.rideId)
            .addSnapshotListener { value, error ->
                value?.let {
                    mListRides = it.toObjects(RideModel::class.java)
                    mAdapter.setList(mListRides)
                }
                Log.d("TAG", error?.message.toString())
            }
    }

    override fun onPause() {
        super.onPause()
        mRidesListener.remove()
    }
}