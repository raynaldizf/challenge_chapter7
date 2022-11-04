package com.example.challenge_chapter6.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.asLiveData
import com.example.challenge_chapter6.MainActivity
import com.example.challenge_chapter6.PreferencesLogin
import com.example.challenge_chapter6.R
import com.example.challenge_chapter6.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    lateinit var sharedPrefs : PreferencesLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefs = PreferencesLogin(this)
        val splashTime : Long = 3000


        Handler().postDelayed({
            sharedPrefs.userName.asLiveData().observe(this,{
                if (it.isNullOrEmpty()){
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            })
        },splashTime)
    }
}