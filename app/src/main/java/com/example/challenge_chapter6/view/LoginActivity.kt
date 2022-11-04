package com.example.challenge_chapter6.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.challenge_chapter6.MainActivity
import com.example.challenge_chapter6.PreferencesLogin
import com.example.challenge_chapter6.R
import com.example.challenge_chapter6.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPrefs: PreferencesLogin
    lateinit var userName: String
    lateinit var password: String

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
    }
}