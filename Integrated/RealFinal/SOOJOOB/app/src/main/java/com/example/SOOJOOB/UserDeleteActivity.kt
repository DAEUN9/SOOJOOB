package com.example.SOOJOOB

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.SOOJOOB.databinding.ActivityUserdeleteBinding
import com.example.SOOJOOB.retrofit.UserDeleteWork

class UserDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserdeleteBinding
    private var deleteFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserdeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun flagCheck() {
            binding.btnDelete.isEnabled = deleteFlag
        }

        flagCheck()

        binding.cbAgree.setOnClickListener {
            deleteFlag = binding.cbAgree.isChecked
            flagCheck()
        }

        binding.btnDelete.setOnClickListener{
            val deleteWork = UserDeleteWork()
            val intent = Intent(this, LoginActivity::class.java)
            deleteWork.work(completion = { status, msg ->
                if (status in 200..300) {
                    Toast.makeText(this@UserDeleteActivity, "$msg", Toast.LENGTH_SHORT).show()
                    App.prefs.setString("X-AUTH-TOKEN", "")
                    startActivity(intent)
                } else {
                    val message = msg?.substring(25 until 37)
                    Toast.makeText(this@UserDeleteActivity, "$message", Toast.LENGTH_SHORT).show()
                }
            })
          }

        binding.btnCancel.setOnClickListener{
            super.onBackPressed()
        }

        binding.backUserupdate.setOnClickListener{
            super.onBackPressed()
        }
    }
}