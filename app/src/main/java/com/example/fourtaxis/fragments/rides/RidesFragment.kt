package com.example.fourtaxis.fragments.rides

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.FIRESTORE
import com.example.fourtaxis.database.RIDES
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_rides.*


class RidesFragment : Fragment(R.layout.fragment_rides) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RidesAdapter
    private lateinit var mRidesListener: ListenerRegistration
    private var mListRides = emptyList<RideModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.mToolbar.title = "Поездки"

        fab_add_ride.setOnClickListener { replaceFragment(AddRideDestFragment()) }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = rides_recycler_view
        mAdapter = RidesAdapter()
        mRecyclerView.adapter = mAdapter

        mRidesListener = FIRESTORE.collection(RIDES).orderBy("date").addSnapshotListener { value, error ->
            value?.let {
                mListRides = it.toObjects(RideModel::class.java)
                mAdapter.setList(mListRides)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mRidesListener.remove()
    }
}