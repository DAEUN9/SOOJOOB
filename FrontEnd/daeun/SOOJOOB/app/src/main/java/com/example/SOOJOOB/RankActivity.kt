package com.example.SOOJOOB

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.databinding.ActivityRankBinding
import com.example.SOOJOOB.retrofit.BadgeWork
import com.example.SOOJOOB.retrofit.RankData
import com.example.SOOJOOB.retrofit.UserData

class RankActivity : AppCompatActivity() {
    private lateinit var recyler_view: RecyclerView


    private lateinit var binding: ActivityRankBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyler_view = findViewById(R.id.rank_recyclerview)

        BadgeWork().getRank(completion = {
            RankList ->
            RankList?.let { setAdapter(it) }


        })

        binding.backRank.setOnClickListener {
            super.onBackPressed()
        }


    }

    private fun setAdapter(RankList : List<RankData>){
        val mAdapter = RankAdapter(RankList)
        recyler_view.adapter = mAdapter
        recyler_view.layoutManager = LinearLayoutManager(this)

        recyler_view.setHasFixedSize(false)
    }




}