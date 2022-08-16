package com.example.SOOJOOB

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.SOOJOOB.databinding.ActivityUserpasswordBinding
import com.example.SOOJOOB.retrofit.PassWordWork
import com.example.SOOJOOB.retrofit.PasswordRequestBody


class PasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserpasswordBinding
    private var passwordFlag = false
    private var passwordCheckFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun passwordRegex(password: String): Boolean {
            return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$".toRegex())
        }

        fun flagCheck() {
            binding.nextButton.isEnabled = passwordFlag && passwordCheckFlag
        }

        flagCheck()

        val passwordListener = object : TextWatcher {
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
                        !passwordRegex(s.toString()) -> {
                            binding.passwordTextInputLayout.error = "비밀번호 양식이 맞지 않습니다."
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

        val passwordRecheckListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    when {
                        s.isEmpty() -> {
                            passwordCheckFlag = false
                        }
                        binding.passwordRecheckTextInputLayout.editText?.text.toString() != binding.passwordTextInputLayout.editText?.text.toString() -> {
                            binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다."
                            passwordCheckFlag = false
                        }
                        else -> {
                            binding.passwordRecheckTextInputLayout.error = null
                            passwordCheckFlag = true
                        }
                    }
                    flagCheck()
                }
            }
        }

        binding.passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)
        binding.passwordTextInputEditText.hint = resources.getString(R.string.password_hint)
        binding.passwordTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passwordTextInputEditText.hint = ""
            } else {
                binding.passwordTextInputEditText.hint =
                    resources.getString(R.string.password_hint)
            }
        }
        binding.passwordRecheckTextInputLayout.editText?.addTextChangedListener(
            passwordRecheckListener
        )

        binding.nextButton.setOnClickListener {
            val password = PasswordRequestBody(
                binding.passwordTextInputLayout.editText?.text.toString()
            )

            val passwordWork = PassWordWork(password)
            val intent = Intent(this, MainActivity::class.java)
            passwordWork.work(completion = { status, msg ->
                if (status in 200..300) {
                    Toast.makeText(this@PasswordActivity, "$msg", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                } else {
                    val msg = msg?.substring(25 until 37)
                    Toast.makeText(this@PasswordActivity, "$msg", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}