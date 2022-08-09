package com.example.proto04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proto04.recyclerview.BadgeGridRecyclerViewAdapter
import com.example.proto04.retrofit.Badge
import kotlinx.android.synthetic.main.activity_badges.*

//class BadgesActivity : AppCompatActivity() {
//
//    private var badgeList = ArrayList<Badge>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_badges)
//
//        val bundle = intent.getBundleExtra("array_bundle")
//        badgeList = bundle?.getSerializable("my_badge_list") as ArrayList<Badge>
//        println(badgeList)
//    }
//}

class BadgesActivity: AppCompatActivity() {


    // 데이터
    private var badgeList = ArrayList<Badge>()
    private var noBadgeList = ArrayList<Badge>()

    // 어답터
    private lateinit var badgeGridRecyclerViewAdapter: BadgeGridRecyclerViewAdapter
    private lateinit var badgeGridRecyclerViewAdapter1: BadgeGridRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badges)

        val bundle = intent.getBundleExtra("array_bundle")
        val bundle1 = intent.getBundleExtra("array_bundle1")

//        println(bundle)
//        val searchTerm = intent.getStringExtra("search_term")

        badgeList = bundle?.getSerializable("my_badge_list") as ArrayList<Badge>
        noBadgeList = bundle1?.getSerializable("no_badge_list") as ArrayList<Badge>

        this.badgeGridRecyclerViewAdapter = BadgeGridRecyclerViewAdapter()

        this.badgeGridRecyclerViewAdapter.submitList(badgeList)



        my_badge_recycler_view.layoutManager = GridLayoutManager(this,
            2,
            GridLayoutManager.VERTICAL,
            false)
        my_badge_recycler_view.adapter = this.badgeGridRecyclerViewAdapter

        this.badgeGridRecyclerViewAdapter1 = BadgeGridRecyclerViewAdapter()
        this.badgeGridRecyclerViewAdapter1.submitList(noBadgeList)



        no_badge_recycler_view.layoutManager = GridLayoutManager(this,
            2,
            GridLayoutManager.VERTICAL,
            false)
        no_badge_recycler_view.adapter = this.badgeGridRecyclerViewAdapter1


    } //


}