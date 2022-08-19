package com.example.SOOJOOB.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.SOOJOOB.*
import com.example.SOOJOOB.databinding.FragmentMypageBinding
import com.example.SOOJOOB.retrofit.BadgeWork
import com.example.SOOJOOB.retrofit.UserWork


class MypageFragment : Fragment() {
    private var userId = 0
    private var fBinding : FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): RelativeLayout? {

        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        fBinding = binding

        binding.badgeButton.setOnClickListener {
            val badgeWork = BadgeWork()


            badgeWork.getMyBadge(completion = { responseBadgeArrayList ->
                val bundle = Bundle()
                val intent = Intent(activity, BadgesActivity::class.java)
                bundle.putSerializable("my_badge_list", responseBadgeArrayList)
                intent.putExtra("array_bundle", bundle)


                startActivity(intent)
            })
        }

        binding.noBadgeButton.setOnClickListener{
            val badgeWork = BadgeWork()
            badgeWork.getNoBadge(completion = { responseBadgeArrayList ->
                val bundle = Bundle()
                val intent = Intent(activity, NoBadgeActivity::class.java)
                bundle.putSerializable("my_badge_list", responseBadgeArrayList)
                intent.putExtra("array_bundle", bundle)


                startActivity(intent)
            })
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(activity, UserupdateActivity::class.java)
            startActivity(intent)
        }

        binding.nextMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }
        binding.myTextButton.setOnClickListener{
            val intent = Intent(activity, MyArticleActivity::class.java)
            intent.putExtra("userId",userId)
            startActivity(intent)
        }

//    override fun onDestroyView() {
//        fBinding = null
//        super.onDestroyView()

        val userWork = UserWork()
        userWork.work(completion = { status, userid, username, trash, exp, badge ->
            if (status in 200..300) {
                binding.mypageUsername.text = username
                binding.mypageMiddleUsername.text = username
                binding.mypageTrash.text = trash.toString()
                binding.mypageExp.text = exp.toString().substring(0, 4)
                binding.mypageProgress.progress = exp!!.toInt()
                binding.mypageBadge.text = badge.toString()
                if (userid != null) {
                    userId = userid
                }
            }
        })

        return fBinding?.root
    }

}