package com.soojoob.frontend.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.soojoob.frontend.R
import kotlinx.android.synthetic.main.fragment_result_plogging.*

class ResultPloggingFragment : Fragment(), View.OnClickListener {

    lateinit var nav_Contoller : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_plogging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nav_Contoller = Navigation.findNavController(view)

//        btn_next.setOnClickListener{
//            nav_Contoller.navigate(R.id.action_selectionFragment_to_resultFragment)
//        }
        btn_home.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_home -> {
                nav_Contoller.navigate(R.id.navigation_home)
            }
        }
    }
}