package com.example.challenge_chapter6.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.asLiveData
import com.example.challenge_chapter6.MainActivity
import com.example.challenge_chapter6.PreferencesLogin
import com.example.challenge_chapter6.R
import com.google.firebase.auth.FirebaseAuth
import com.example.challenge_chapter6.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPrefs: PreferencesLogin
    lateinit var userName: String
    lateinit var password: String
    lateinit var auth : FirebaseAuth
    lateinit var signGoogle : GoogleSignInClient
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            results(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signGoogle = GoogleSignIn.getClient(this, gso)

        binding.btnLoginGoogle.setOnClickListener{
            signIn()
        }

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

    }
    fun signIn(){
        val signinClient = signGoogle.signInIntent
        launcher.launch(signinClient)
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