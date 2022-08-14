package com.example.SOOJOOB

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.SOOJOOB.databinding.ActivityUserupdateBinding
import com.example.SOOJOOB.retrofit.UserInfoWork
import com.example.SOOJOOB.retrofit.UserWork
import com.example.SOOJOOB.retrofit.UserupdateRequestBody
import com.example.SOOJOOB.retrofit.UserupdateWork

class UserupdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserupdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserupdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userInfoWork = UserInfoWork()
        userInfoWork.work(completion = { statusCode, username, email, age, gender, region ->
            if (statusCode in 200..300) {
                binding.usernameTextInputEditText.hint = username
                binding.emailTextInputEditText.setText(email)
                binding.ageTextInputEditText.hint = age.toString()
                binding.regionTextInputEditText.hint = region.toString()
            } else {
                val msg = "회원정보 로딩에 실패했습니다."
                Toast.makeText(this@UserupdateActivity, "$msg", Toast.LENGTH_SHORT).show()
            }
        })

        binding.nextButton.setOnClickListener {
            val userDataUpdate = UserupdateRequestBody(
                binding.ageTextInputLayout.editText?.text.toString(),
                binding.emailTextInputLayout.editText?.text.toString(),
                binding.regionTextInputLayout.editText?.text.toString(),
                binding.usernameTextInputLayout.editText?.text.toString()
            )

            val userupdateWork = UserupdateWork(userDataUpdate)
            val intent = Intent(this, MainActivity::class.java)
            userupdateWork.work(completion = { status, msg ->
                if (status in 200..300) {
                    Toast.makeText(this@UserupdateActivity, "$msg", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    val msg = msg?.substring(25 until 37)
                    Toast.makeText(this@UserupdateActivity, "$msg", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}