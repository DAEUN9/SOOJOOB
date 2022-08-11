package com.example.proto04

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

class PloggingAdapter(val context: Context, val ploggingList: List<Result>) :
    RecyclerView.Adapter<PloggingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PloggingAdapter.ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_rec,parent ,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ploggingList[position],context)
    }

    override fun getItemCount(): Int {
        return ploggingList.count()
    }




    class ViewHolder (itemView: View? ) : RecyclerView.ViewHolder(itemView!!){


        val image = itemView?.findViewById<ImageView>(R.id.image)
        val distance = itemView?.findViewById<TextView>(R.id.distance)
        val recTime  = itemView?.findViewById<TextView>(R.id.recTime)
        val trashCnt =   itemView?.findViewById<TextView>(R.id.trashCnt)
        val date =  itemView?.findViewById<TextView>(R.id.date)
        private lateinit var imageString:String

        fun String.toBitmap(): Bitmap?{
            Base64.decode(this, Base64.DEFAULT).apply {
                return BitmapFactory.decodeByteArray(this,0,size)
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(itemPlogging : Result?, context: Context){
//            image?.visibility = View.GONE
            imageString = itemPlogging?.ploggingImg as String
            image?.setImageBitmap(imageString.toBitmap())
            distance?.text = itemPlogging?.distance.toString()
            recTime?.text = (itemPlogging?.timeRecord?.div(100)).toString() + "\""  + itemPlogging?.timeRecord?.rem(100).toString()
            trashCnt?.text = itemPlogging?.trashCount.toString() + "개"
            date?.text = itemPlogging?.dateTime

        }

    }

}