package com.example.proto04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.proto04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var fBinding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(fBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(fBinding.myBottomNav, navController)

    }
}