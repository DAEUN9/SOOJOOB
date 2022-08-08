package com.example.proto04

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.example.proto04.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private var emailFlag = false
    private var usernameFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextButton.isEnabled = false

        binding.emailTextInputLayout.editText?.addTextChangedListener(emailListener)
        binding.emailTextInputEditText.hint = resources.getString(R.string.email_hint)
        binding.emailTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.emailTextInputEditText.hint = ""
            } else {
                binding.emailTextInputEditText.hint = resources.getString(R.string.email_hint)
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

        binding.usernameTextInputLayout.editText?.addTextChangedListener(usernameListener)


        binding.nextButton.setOnClickListener {
            val userData = SignUpRequestBody(
                binding.emailTextInputLayout.editText?.text.toString(),
                binding.usernameTextInputLayout.editText?.text.toString(),
                binding.passwordTextInputLayout.editText?.text.toString()
            )
            val signupWork = SignupWork(userData)
            signupWork.work()
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

    private val passwordRecheckListener = object : TextWatcher {
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

    private val usernameListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.usernameTextInputLayout.error = "별명은 필수 입력 항목입니다."
                        usernameFlag = false
                    }
                    else -> {
                        binding.usernameTextInputLayout.error = null
                        usernameFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    // 특수문자 존재 여부를 확인하는 메서드
    private fun hasSpecialCharacter(string: String): Boolean {
        for (i in string.indices) {
            if (!Character.isLetterOrDigit(string[i])) {
                return true
            }
        }
        return false
    }

    // 영문자 존재 여부를 확인하는 메서드
    private fun hasAlphabet(string: String): Boolean {
        for (i in string.indices) {
            if (Character.isAlphabetic(string[i].code)) {
                return true
            }
        }
        return false
    }

    fun emailRegex(email: String): Boolean {
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex())
    }

    fun passwordRegex(password: String): Boolean {
        return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$".toRegex())
    }

    fun flagCheck() {
        binding.nextButton.isEnabled = emailFlag && passwordFlag && passwordCheckFlag && usernameFlag
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }
}