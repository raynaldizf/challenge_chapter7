package com.example.challenge_chapter6.view

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.challenge_chapter6.MainActivity
import com.example.challenge_chapter6.PreferencesLogin
import com.example.challenge_chapter6.R
import com.example.challenge_chapter6.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPrefs: PreferencesLogin
    lateinit var userName: String
    lateinit var password: String
    lateinit var auth : FirebaseAuth
    lateinit var signGoogle : GoogleSignInClient
    val requestCode : Int = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = PreferencesLogin(this)
        sharedPrefs.userName.asLiveData().observe(this, {
            userName = it.toString()
        })

        sharedPrefs.userPassword.asLiveData().observe(this, {
            password = it.toString()
        })

        binding.btnLogin.setOnClickListener {
            if (binding.etUsername.text.isNotEmpty() || binding.etPassword.text.isNotEmpty()) {
                if (binding.etUsername.text.toString() == userName && binding.etPassword.text.toString() == password) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Creates a button that mimics a crash when pressed
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))

        binding.btnLoginGoogle.setOnClickListener{
            FirebaseApp.initializeApp(this)
            val gsos = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            signGoogle = GoogleSignIn.getClient(this, gsos)
            auth = FirebaseAuth.getInstance()
            Toast.makeText(this, "Login with Google", Toast.LENGTH_SHORT).show()
            signIn()

        }
    }
    fun signIn(){
        val signinClient : Intent = signGoogle.signInIntent
        startActivityForResult(signinClient, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==requestCode){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            results(task)

        }
    }

    fun results(task : Task<GoogleSignInAccount>){
        try {
            val account = task.getResult(Exception::class.java)
            if (account != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }catch (e : Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}