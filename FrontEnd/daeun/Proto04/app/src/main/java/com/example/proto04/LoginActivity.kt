package com.example.proto04

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import com.example.proto04.databinding.ActivityLoginBinding
import com.example.proto04.retrofit.BadgeWork

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var emailFlag = false
    private var passwordFlag = false


    // Noti 객체 생성
    private lateinit var notificationHelper: NotificationHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextButton.isEnabled = false

        binding.emailTextInputLayout.editText?.addTextChangedListener(emailListener)

        binding.passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)

//        badgewWork.getMyBadge(userId = "1", completion = { responseBadgeArrayList ->
//
//            badgewWork.getNoBadge(userId = "1", completion = { responseBadgeArrayList1 ->
//                bundle.putSerializable("my_badge_list", responseBadgeArrayList)
//                intent.putExtra("array_bundle", bundle)
//
//                bundle1.putSerializable("no_badge_list", responseBadgeArrayList1)
//                intent.putExtra("array_bundle1", bundle1)
//
//                startActivity(intent)
//            })
//        }
//
//
//        )

        binding.nextButton.setOnClickListener {
            val userData = LoginRequestBody(
                binding.emailTextInputLayout.editText?.text.toString(),
                binding.passwordTextInputLayout.editText?.text.toString()
            )
            val loginWork = LoginWork(userData)
            val badgeWork = BadgeWork()
            var cnt = 0
            loginWork.work( completion = { status ->
                badgeWork.Myplogging ( completion = { ploggingResponse ->

                    if (ploggingResponse != null) {
                        for (plogging in ploggingResponse) {
                            notificationHelper = NotificationHelper(this)
                            cnt += 1
                            val title: String = "${plogging.distance} 만큼 이동했어요"
                            val message: String = "${plogging.ploggingUser}번 유저"

                            // 알림 호출ㅇ
                            showNotification(title, message, cnt)
                        }

                    }
                } )
            }
            )

        }

        binding.logoLogin.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

        binding.nextSignup.setOnClickListener {
            val nextIntent = Intent(this, SignupActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private val emailListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.emailTextInputLayout.error = "이메일을 입력해주세요."
                        emailFlag = false
                    }
                    !emailRegex(s.toString()) -> {
                        binding.emailTextInputLayout.error = "이메일 양식이 맞지 않습니다"
                        emailFlag = false
                    }
                    else -> {
                        binding.emailTextInputLayout.error = null
                        emailFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val passwordListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.passwordTextInputLayout.error = "비밀번호를 입력해주세요."
                        passwordFlag = false
                    }
                    else -> {
                        binding.passwordTextInputLayout.error = null
                        passwordFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    fun emailRegex(email: String): Boolean {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex())
    }

    fun flagCheck() {
        binding.nextButton.isEnabled = emailFlag && passwordFlag
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    private fun showNotification(title: String, message: String, id: Int) {
        val nb: NotificationCompat.Builder =
            notificationHelper.getChannelNotification(title, message)
        notificationHelper.getManager().notify(id, nb.build())
    }
}