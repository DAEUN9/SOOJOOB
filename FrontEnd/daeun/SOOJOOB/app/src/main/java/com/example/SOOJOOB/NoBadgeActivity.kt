package com.example.SOOJOOB

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.SOOJOOB.databinding.ActivityNoBadgeBinding
import com.example.SOOJOOB.retrofit.Badge
import com.example.SOOJOOB.views.ContentAdapter

class NoBadgeActivity : AppCompatActivity() {
    // 데이터
    private var badgeList = ArrayList<Badge>()



    // 어답터
//    private lateinit var badgeGridRecyclerViewAdapter: BadgeGridRecyclerViewAdapter
//    private lateinit var badgeGridRecyclerViewAdapter1: BadgeGridRecyclerViewAdapter
    private lateinit var contentAdapter: ContentAdapter


    private lateinit var binding: ActivityNoBadgeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoBadgeBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.backSignup.setOnClickListener {
            super.onBackPressed()
        }


        val bundle = intent.getBundleExtra("array_bundle")



        badgeList = bundle?.getSerializable("my_badge_list") as ArrayList<Badge>


        this.contentAdapter = ContentAdapter(this, badgeList)


        binding.noBadges.adapter = contentAdapter

        binding.noBadges.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
//특정 리스트 클릭 시 실행

                val selection = parent.getItemAtPosition(position) as Badge
                //클릭한 포지션을 가진 변수가 만들어짐

                Toast.makeText(this, "${selection.badgeDetail}", Toast.LENGTH_SHORT).show()
                //토스트 팝업으로 클릭한 리스트의 변수값이 뿅하고 나옴

                showBadgeDialog(selection.badgeName, selection.badgeDetail, selection.imgUrl)

            }
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
            .placeholder(R.drawable.boy)
            .into(imgView)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle(" ")
            .create()

        val btnView = view.findViewById<Button>(R.id.dialog_ok)
        btnView.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.setView(view)
        alertDialog.show()
    }
}