package com.example.SOOJOOB

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.SOOJOOB.databinding.ActivityBadgesBinding
import com.example.SOOJOOB.retrofit.Badge
import com.example.SOOJOOB.retrofit.UserWork
import com.example.SOOJOOB.views.ContentAdapter



class BadgesActivity : AppCompatActivity() {
    // 데이터
    private var badgeList = ArrayList<Badge>()



    // 어답터
//    private lateinit var badgeGridRecyclerViewAdapter: BadgeGridRecyclerViewAdapter
//    private lateinit var badgeGridRecyclerViewAdapter1: BadgeGridRecyclerViewAdapter
    private lateinit var contentAdapter: ContentAdapter


    private lateinit var binding: ActivityBadgesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBadgesBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)



        binding.backBadge.setOnClickListener {
            super.onBackPressed()
        }


        val bundle = intent.getBundleExtra("array_bundle")



        badgeList = bundle?.getSerializable("my_badge_list") as ArrayList<Badge>


        this.contentAdapter = ContentAdapter(this, badgeList)


        binding.myBadges.adapter = contentAdapter

        binding.myBadges.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
//특정 리스트 클릭 시 실행

                val selection = parent.getItemAtPosition(position) as Badge
                //클릭한 포지션을 가진 변수가 만들어짐

                Toast.makeText(this, "${selection.badgeDetail}", Toast.LENGTH_SHORT).show()
                //토스트 팝업으로 클릭한 리스트의 변수값이 뿅하고 나옴

                showBadgeDialog(selection.badgeName, selection.badgeDetail, selection.imgUrl)

            }

        val userWork = UserWork()
        userWork.work(completion = { status, username, trash, exp, badge ,id->
            if (status in 200..300) {
                binding.badgeCount.text = badge.toString()
                when (exp?.toInt()) {
                    in 40..45 -> {
                        binding.mybadgeName.text = "나는야 실버"
                        binding.mybadgeImage.setImageResource(R.drawable.ic_silver)
                    }
                    in 45..50 -> {
                        binding.mybadgeName.text = "24K 골드"
                        binding.mybadgeImage.setImageResource(R.drawable.ic_gold)
                    }
                    in 50..100 -> {
                        binding.mybadgeName.text = "나는 전설이다"
                        binding.mybadgeImage.setImageResource(R.drawable.ic_trophy)
                    }
                    else -> {
                        binding.mybadgeName.text = "뉴비가 나타났다"
                    }
                }
            }
        })
    }
    private fun showBadgeDialog(title: String?, content: String?, img: String?) {

        // 다이얼로그 화면 설정
        val inflater : LayoutInflater = layoutInflater
        val view = inflater.inflate(R.layout.custom_dialog, null)

        val titleView: TextView = view.findViewById(R.id.dialog_title)
        val contentView: TextView = view.findViewById(R.id.dialog_content)
        val imgView: ImageView = view.findViewById(R.id.dialog_img)


        titleView.text = title
        contentView.text = content
        Glide.with(view)
            .load(img)
            .placeholder(R.drawable.ic_lock)
            .into(imgView)

        val alertDialog = AlertDialog.Builder(this)
            .create()

        val btnView = view.findViewById<Button>(R.id.dialog_ok)
        btnView.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }
}