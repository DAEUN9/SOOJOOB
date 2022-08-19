package com.example.proto04.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proto04.R
import com.example.proto04.retrofit.Badge


//class PhotoGridRecyeclerViewAdapter(photoList: ArrayList<Photo>) : RecyclerView.Adapter<PhotoItemViewHolder>(){

class BadgeGridRecyclerViewAdapter : RecyclerView.Adapter<BadgeItemViewHolder>(){


    private var badgeList = ArrayList<Badge>()

    // 뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeItemViewHolder {

        val badgeItemViewHolder = BadgeItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_badge_item, parent, false),
        )

        return badgeItemViewHolder
    }

    // 보여줄 목록의 갯수
    override fun getItemCount(): Int {
        return this.badgeList.size
    }

    // 뷰가 묶였을때 데이터를 뷰홀더에 넘겨준다.
    override fun onBindViewHolder(holder: BadgeItemViewHolder, position: Int) {
        holder.bindWithView(this.badgeList[position])
    }

    // 외부에서 어답터에 데이터 배열을 넣어준다.
    fun submitList(badgeList: ArrayList<Badge>){
        this.badgeList = badgeList
    }

}