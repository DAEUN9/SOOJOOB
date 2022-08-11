package com.example.SOOJOOB.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.SOOJOOB.MapsActivity
import com.example.SOOJOOB.databinding.FragmentHomeBinding
import com.example.SOOJOOB.retrofit.UserWork

class HomeFragment : Fragment() {

    private var fBinding : FragmentHomeBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fBinding = binding

//        binding.homeNotification.setOnClickListener {
//            val intent = Intent(activity, NotificationActivity::class.java)
//            startActivity(intent)
//        }

//        binding.logout.setOnClickListener {
//            val intent = Intent(activity, LogoutActivity::class.java)
//            startActivity(intent)
//        }

        binding.nextMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        val userWork = UserWork()
        userWork.work(completion = { status, username, trash, exp ->
            if (status in 200..300) {
                binding.homeUsername.text = username
                binding.homeTrash.text = trash.toString()
                binding.homeExp.text = exp.toString()
                binding.homeProgress.progress = exp!!.toInt()
            }
        })

        return fBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fBinding = null
    }


}