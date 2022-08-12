package com.example.proto04.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proto04.LoginActivity
import com.example.proto04.MapsActivity
import com.example.proto04.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var fBinding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fBinding = binding

        binding.login.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.nextMaps.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        return fBinding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fBinding = null
    }


}