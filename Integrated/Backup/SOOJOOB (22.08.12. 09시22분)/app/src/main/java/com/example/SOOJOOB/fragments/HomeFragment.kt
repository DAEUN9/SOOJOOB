package com.example.SOOJOOB.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.SOOJOOB.MapsActivity
import com.example.SOOJOOB.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var fBinding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fBinding = binding

//        binding.logout.setOnClickListener {
//            val intent = Intent(activity, LogoutActivity::class.java)
//            startActivity(intent)
//        }

        binding.nextMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }


        val username = "김다은"

        binding.homeUsername.text = username


        return fBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fBinding = null
    }


}