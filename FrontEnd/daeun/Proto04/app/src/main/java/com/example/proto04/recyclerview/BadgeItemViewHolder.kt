package com.example.proto04.recyclerview

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proto04.App
import com.example.proto04.R
import com.example.proto04.retrofit.Badge
import kotlinx.android.synthetic.main.layout_badge_item.view.*


class BadgeItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {


    // 뷰들을 가져온다.

    // 뷰들을 가져온다.
    private val badgeImageView = itemView.badge_image
    private val badgeTitle = itemView.badge_title
    private val badgeContent = itemView.badge_content


    // 데이터와 뷰를 묶는다.
    fun bindWithView(badgeItem: Badge){
        Log.d(TAG, "PhotoItemViewHolder - bindWithView() called")

        badgeContent.text = badgeItem.badgeDetail

        badgeTitle.text = badgeItem.badgeName
        // 이미지를 설정한다.
        Glide.with(itemView
        )
            .load(badgeItem.imgUrl)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(badgeImageView)


    }


}