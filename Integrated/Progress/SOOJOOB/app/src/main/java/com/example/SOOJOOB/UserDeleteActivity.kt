package com.example.SOOJOOB

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.SOOJOOB.databinding.ActivityUserdeleteBinding

class UserDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserdeleteBinding
    private var deleteFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserdeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun flagCheck() {
            binding.nextButton.isEnabled = passwordFlag && passwordCheckFlag
        }
    }
}