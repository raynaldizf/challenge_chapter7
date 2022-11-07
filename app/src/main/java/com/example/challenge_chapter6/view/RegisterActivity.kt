package com.example.challenge_chapter6.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge_chapter6.PreferencesLogin
import com.example.challenge_chapter6.databinding.ActivityRegisterBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    lateinit var sharedPrefs : PreferencesLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefs = PreferencesLogin(this)

        binding.btnDaftar.setOnClickListener{
            var userName = binding.etUsername.text.toString()
            var password = binding.etPassword.text.toString()
            var email = binding.etEmail.text.toString()
            var namaLengkap = binding.etNamaLengkap.text.toString()
            var alamat = binding.etAlamat.text.toString()
            var tanggalLahir = binding.etTanggalLahir.text.toString()
            GlobalScope.launch {
                sharedPrefs.dataSaveRegister(userName,email, password, namaLengkap, alamat, tanggalLahir)
                Toast.makeText(this@RegisterActivity, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }
}