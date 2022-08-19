package com.example.proto04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proto04.fragments.HomeFragment
import com.example.proto04.recyclerview.BadgeGridRecyclerViewAdapter
import com.example.proto04.recyclerview.ContentAdapter
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
//    private lateinit var badgeGridRecyclerViewAdapter: BadgeGridRecyclerViewAdapter
//    private lateinit var badgeGridRecyclerViewAdapter1: BadgeGridRecyclerViewAdapter
    private lateinit var contentAdapter: ContentAdapter
    private lateinit var contentAdapter2: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badges)

        val bundle = intent.getBundleExtra("array_bundle")
        val bundle1 = intent.getBundleExtra("array_bundle1")





        badgeList = bundle?.getSerializable("my_badge_list") as ArrayList<Badge>
        noBadgeList = bundle1?.getSerializable("no_badge_list") as ArrayList<Badge>

//        this.badgeGridRecyclerViewAdapter = BadgeGridRecyclerViewAdapter()
//
//        this.badgeGridRecyclerViewAdapter.submitList(badgeList)



//        my_badge_recycler_view.layoutManager = GridLayoutManager(this,
//            2,
//            GridLayoutManager.VERTICAL,
//            false)
//        my_badge_recycler_view.adapter = this.badgeGridRecyclerViewAdapter
//
//        this.badgeGridRecyclerViewAdapter1 = BadgeGridRecyclerViewAdapter()
//        this.badgeGridRecyclerViewAdapter1.submitList(noBadgeList)
//
//
//
//        no_badge_recycler_view.layoutManager = GridLayoutManager(this,
//            2,
//            GridLayoutManager.VERTICAL,
//            false)
//        no_badge_recycler_view.adapter = this.badgeGridRecyclerViewAdapter1

        this.contentAdapter = ContentAdapter(this, badgeList)
        this.contentAdapter2 = ContentAdapter(this, noBadgeList)

        myBadges.adapter = contentAdapter

        myBadges.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//특정 리스트 클릭 시 실행

            val selection = parent.getItemAtPosition(position) as Badge
            //클릭한 포지션을 가진 변수가 만들어짐

            Toast.makeText(this, "${selection.badgeDetail}", Toast.LENGTH_SHORT).show()
            //토스트 팝업으로 클릭한 리스트의 변수값이 뿅하고 나옴

        }

        noBadges.adapter = contentAdapter2

        noBadges.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->


            val selection = parent.getItemAtPosition(position) as Badge
            //클릭한 포지션을 가진 변수가 만들어짐

            Toast.makeText(this, "${selection.badgeDetail}", Toast.LENGTH_SHORT).show()
            //토스트 팝업으로 클릭한 리스트의 변수값이 뿅하고 나옴
        }
    } //


}