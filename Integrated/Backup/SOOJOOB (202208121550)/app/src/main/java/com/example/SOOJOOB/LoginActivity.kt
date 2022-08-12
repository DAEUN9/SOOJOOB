package com.example.SOOJOOB

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.SOOJOOB.databinding.ActivityLoginBinding
import com.example.SOOJOOB.retrofit.LoginRequestBody
import com.example.SOOJOOB.retrofit.LoginWork


class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var emailFlag = false
    private var passwordFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextButton.isEnabled = false

        binding.emailTextInputLayout.editText?.addTextChangedListener(emailListener)

        binding.passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)

        binding.nextButton.setOnClickListener {
            val userData = LoginRequestBody(
                binding.emailTextInputLayout.editText?.text.toString(),
                binding.passwordTextInputLayout.editText?.text.toString()
            )


            val loginWork = LoginWork(userData)
            val intent = Intent(this, MainActivity::class.java)
            loginWork.work(completion = { status ->
                if (status in 200..300) {
                    Toast.makeText(this@LoginActivity, "로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "이메일 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            })
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
}