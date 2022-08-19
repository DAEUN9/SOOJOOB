package com.example.proto04.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.proto04.R
import com.example.proto04.retrofit.Badge

class ContentAdapter (val context: Context, val ContentList : ArrayList<Badge>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_badge_item, null)

        val badgeContent = view.findViewById<TextView>(R.id.badge_content)
        val badgeTitle = view.findViewById<TextView>(R.id.badge_title)
        val badgeImg = view.findViewById<ImageView>(R.id.badge_image)

        // 이미지를 설정한다.


        val content = ContentList[position]

        badgeContent.text = content.badgeDetail
        badgeTitle.text = content.badgeName
        Glide.with(context)
            .load(content.imgUrl)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(badgeImg)

        return view
    }

    override fun getItem(position: Int): Any {
        return ContentList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return ContentList.size
    }
}