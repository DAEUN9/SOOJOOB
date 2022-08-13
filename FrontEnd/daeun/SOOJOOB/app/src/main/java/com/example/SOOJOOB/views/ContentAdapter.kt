package com.example.SOOJOOB.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.SOOJOOB.R
import com.example.SOOJOOB.retrofit.Badge

class ContentAdapter (val context: Context, val ContentList : ArrayList<Badge>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_badge_item, null)


        val badgeImg = view.findViewById<ImageView>(R.id.badge_image)

        // 이미지를 설정한다.


        val content = ContentList[position]

        Glide.with(context)
            .load(content.imgUrl)
            .placeholder(R.drawable.ic_arrow_forward)
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