package com.example.SOOJOOB

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

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

        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(currentDateTime)

        val title = itemView?.findViewById<TextView>(R.id.title)
        val contents  = itemView?.findViewById<TextView>(R.id.contents)
        val createdDate = itemView?.findViewById<TextView>(R.id.createdDate)
        val userName = itemView?.findViewById<TextView>(R.id.userName)
        val articleImage = itemView?.findViewById<ImageView>(R.id.image)

        fun String.toBitmap(): Bitmap?{
            Base64.decode(this, Base64.DEFAULT).apply {
                return BitmapFactory.decodeByteArray(this,0,size)
            }
        }

        fun encodeImage(bm: Bitmap?):ByteArray {
            val baos = ByteArrayOutputStream()
            bm?.compress(Bitmap.CompressFormat.PNG, 10, baos)
            val b = baos.toByteArray()
            return b
        }

        @SuppressLint("SetTextI18n")
        fun bind(itemArticle : Article?){

            title?.text = itemArticle?.title
            contents?.text = itemArticle?.contents
            createdDate?.text = itemArticle?.createdDate
            userName?.text = itemArticle?.userName
            articleImage?.setImageBitmap(itemArticle?.articleImage?.toBitmap())
            val ba = encodeImage(itemArticle?.articleImage?.toBitmap())
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArticleDetailActivity::class.java)

                //intent.putExtra("data", itemArticle)
                intent.putExtra("title", itemArticle?.title.toString())

                intent.putExtra("contents", itemArticle?.contents.toString())
                intent.putExtra("createdDate", dateFormat)
                intent.putExtra("userName", itemArticle?.userName)
                intent.putExtra("articleImage", ba)

                intent.run { itemView.context.startActivity(this) }
            }
        }

    }

}