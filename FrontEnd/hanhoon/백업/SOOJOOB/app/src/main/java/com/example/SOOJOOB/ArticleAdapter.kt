package com.example.SOOJOOB

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.fragments.CommunityFragment

class ArticleAdapter(val articleList: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article,parent ,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.count()
    }




    class ViewHolder (itemView: View ) : RecyclerView.ViewHolder(itemView){


        val title = itemView?.findViewById<TextView>(R.id.title)
        val contents  = itemView?.findViewById<TextView>(R.id.contents)
        val createdDate =   itemView?.findViewById<TextView>(R.id.createdDate)


        @SuppressLint("SetTextI18n")
        fun bind(itemArticle : Article?){

            title?.text = itemArticle?.title
            contents?.text = itemArticle?.contents
            createdDate?.text = itemArticle?.createdDate

        }

    }

}