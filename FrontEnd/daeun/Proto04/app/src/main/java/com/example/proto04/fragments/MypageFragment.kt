package com.example.proto04.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.proto04.BadgesActivity
import com.example.proto04.LoginWork
import com.example.proto04.MapsActivity
import com.example.proto04.databinding.FragmentMypageBinding
import com.example.proto04.retrofit.BadgeWork
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MypageFragment : Fragment() {

    private var fBinding : FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): RelativeLayout? {

        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        fBinding = binding

        binding.badgeButton.setOnClickListener {
            val badgewWork = BadgeWork()
            val bundle = Bundle()
            val bundle1 = Bundle()
            val intent = Intent(activity, BadgesActivity::class.java)

            badgewWork.getMyBadge(userId = "1", completion = { responseBadgeArrayList ->

                    badgewWork.getNoBadge(userId = "1", completion = { responseBadgeArrayList1 ->
                        bundle.putSerializable("my_badge_list", responseBadgeArrayList)
                        intent.putExtra("array_bundle", bundle)

                        bundle1.putSerializable("no_badge_list", responseBadgeArrayList1)
                        intent.putExtra("array_bundle1", bundle1)

                        startActivity(intent)
                    })
            }


            )






//            val intent = Intent(activity, BadgesActivity::class.java)





// 3
            }



//    override fun onDestroyView() {
//        fBinding = null
//        super.onDestroyView()

        return fBinding?.root
    }

}
