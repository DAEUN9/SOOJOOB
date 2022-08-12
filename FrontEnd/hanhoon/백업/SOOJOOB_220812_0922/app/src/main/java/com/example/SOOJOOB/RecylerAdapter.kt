package com.example.SOOJOOB
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val ploggingList: List<Result>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rec2, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.distance?.text = ploggingList[i].distance.toString()
        viewHolder.date?.text = ploggingList[i].dateTime
        viewHolder.recTime?.text = ploggingList[i].timeRecord.toString()

        viewHolder.trashCnt?.text = ploggingList[i].trashCount.toString()
        viewHolder.imageString = ploggingList[i].ploggingImg as String
        viewHolder.image?.setImageBitmap(viewHolder.imageString.toBitmap())

    }

    override fun getItemCount(): Int {
        return ploggingList.count()
    }
    fun String.toBitmap(): Bitmap?{
        Base64.decode(this, Base64.DEFAULT).apply {
            return BitmapFactory.decodeByteArray(this,0,size)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView?.findViewById<ImageView>(R.id.image)
        val distance = itemView?.findViewById<TextView>(R.id.distance)
        val recTime  = itemView?.findViewById<TextView>(R.id.recTime)
        val trashCnt =   itemView?.findViewById<TextView>(R.id.trashCnt)
        val date =  itemView?.findViewById<TextView>(R.id.date)
        lateinit var imageString:String

        }




}


