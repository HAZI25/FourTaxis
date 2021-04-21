package com.example.fourtaxis.fragments.rides

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fourtaxis.R
import com.example.fourtaxis.database.FIRESTORE
import com.example.fourtaxis.database.RIDES
import com.example.fourtaxis.models.RideModel
import com.example.fourtaxis.utils.APP_ACTIVITY
import com.example.fourtaxis.utils.replaceFragment
import kotlinx.android.synthetic.main.fragment_rides.*


class RidesFragment : Fragment(R.layout.fragment_rides) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RidesAdapter
    private var mListRides = emptyList<RideModel>()

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mToolbar.title = "Rides"

        fab_add_ride.setOnClickListener { replaceFragment(AddRideDestFromFragment()) }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = rides_recycler_view
        mAdapter = RidesAdapter()
        mRecyclerView.adapter = mAdapter

        FIRESTORE.collection(RIDES).orderBy("date").addSnapshotListener { value, error ->
            value?.let {
                mListRides = it.toObjects(RideModel::class.java)
                mAdapter.setList(mListRides)
            }
        }
    }
}