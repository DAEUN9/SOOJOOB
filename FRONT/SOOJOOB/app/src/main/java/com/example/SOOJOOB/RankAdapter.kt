package com.example.SOOJOOB

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.SOOJOOB.retrofit.RankData
import com.gun0912.tedpermission.provider.TedPermissionProvider.context

class RankAdapter(val RankList: List<RankData>) :
    RecyclerView.Adapter<RankAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RankAdapter.ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rank,parent ,false))


    }

    override fun onBindViewHolder(holder: RankAdapter.ViewHolder, position: Int) {
        holder.bind(RankList[position], context)
    }

    override fun getItemCount(): Int {
        return RankList.count()
    }




    class ViewHolder (itemView: View? ) : RecyclerView.ViewHolder(itemView!!){


        val rankNum = itemView?.findViewById<TextView>(R.id.rank_num)
        val username = itemView?.findViewById<TextView>(R.id.username)
        val exp = itemView?.findViewById<TextView>(R.id.exp)


        fun bind(itemRank: RankData, context: Context){
            rankNum?.text = (position+1).toString()
            username?.text = itemRank?.userRecord?.username
            exp?.text = itemRank?.exp.toString().substring(0, 4)+"℃"

        }

    }

}