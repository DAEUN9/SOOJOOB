package com.example.SOOJOOB

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.SOOJOOB.databinding.ActivityBadgesBinding
import com.example.SOOJOOB.databinding.ActivityLoginBinding
import com.example.SOOJOOB.retrofit.Badge
import com.example.SOOJOOB.views.ContentAdapter



class BadgesActivity: AppCompatActivity() {


    // 데이터
    private var badgeList = ArrayList<Badge>()
    private var noBadgeList = ArrayList<Badge>()



    // 어답터
//    private lateinit var badgeGridRecyclerViewAdapter: BadgeGridRecyclerViewAdapter
//    private lateinit var badgeGridRecyclerViewAdapter1: BadgeGridRecyclerViewAdapter
    private lateinit var contentAdapter: ContentAdapter
    private lateinit var contentAdapter2: ContentAdapter


    private lateinit var binding: ActivityBadgesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBadgesBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)



        val bundle = intent.getBundleExtra("array_bundle")
        val bundle1 = intent.getBundleExtra("array_bundle1")



        badgeList = bundle?.getSerializable("my_badge_list") as ArrayList<Badge>
        noBadgeList = bundle1?.getSerializable("no_badge_list") as ArrayList<Badge>


        this.contentAdapter = ContentAdapter(this, badgeList)
        this.contentAdapter2 = ContentAdapter(this, noBadgeList)


        binding.myBadges.adapter = contentAdapter

        binding.myBadges.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//특정 리스트 클릭 시 실행

            val selection = parent.getItemAtPosition(position) as Badge
            //클릭한 포지션을 가진 변수가 만들어짐

            Toast.makeText(this, "${selection.badgeDetail}", Toast.LENGTH_SHORT).show()
            //토스트 팝업으로 클릭한 리스트의 변수값이 뿅하고 나옴

        }

        binding.noBadges.adapter = contentAdapter2

        binding.noBadges.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->


            val selection = parent.getItemAtPosition(position) as Badge
            //클릭한 포지션을 가진 변수가 만들어짐

            Toast.makeText(this, "${selection.badgeDetail}", Toast.LENGTH_SHORT).show()
            //토스트 팝업으로 클릭한 리스트의 변수값이 뿅하고 나옴
        }
    } //


}