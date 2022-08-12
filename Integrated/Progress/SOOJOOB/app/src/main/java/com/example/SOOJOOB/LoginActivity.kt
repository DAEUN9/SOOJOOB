package com.example.SOOJOOB

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.SOOJOOB.databinding.ActivityLoginBinding
import com.example.SOOJOOB.retrofit.GoogleSignupWork
import com.example.SOOJOOB.retrofit.GoogleSinupReqBody
import com.example.SOOJOOB.retrofit.LoginRequestBody
import com.example.SOOJOOB.retrofit.LoginWork
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var emailFlag = false
    private var passwordFlag = false

    // 구글 로그인
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth


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

        // 구글 로그인
        auth = FirebaseAuth.getInstance()



        // Configure Google Signin
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("639304711953-b2abpkijh5f29plt1rk9mo71umepsnmf.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)



        binding.googleLogin.setOnClickListener{
            GoogleSignIn()
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

    private fun GoogleSignIn() {
        val signInIntent = googleSignInClient.signInIntent;
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Result returned from lauching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was sccessful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                val userData = GoogleSinupReqBody(
                    account.email,
                    account.displayName,
                    account.id,
                )
                GoogleSignupWork().work(userData)
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user/
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)
        }
    }

    companion object{
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA NAME"
    }
}