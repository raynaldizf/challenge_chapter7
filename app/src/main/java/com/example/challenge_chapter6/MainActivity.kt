package com.example.challenge_chapter6

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_chapter6.databinding.ActivityMainBinding
import com.example.challenge_chapter6.view.AdapterFilm
import com.example.challenge_chapter6.view.AddMovieActivity
import com.example.challenge_chapter6.view.FavoriteActivity
import com.example.challenge_chapter6.view.ProfileActivity
import com.example.challenge_chapter6.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var sharedPrefs : PreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefs = PreferencesLogin(this)

        setVMtoAdapter()
        binding.btnFavorite.setOnClickListener{
            startActivity(Intent(this,FavoriteActivity::class.java))
        }

        binding.btnAdd.setOnClickListener{
            startActivity(Intent(this,AddMovieActivity::class.java))
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }

        sharedPrefs.userName.asLiveData().observe(this,{
            binding.txtUser.text = it.toString()
        })


    }

    fun setVMtoAdapter(){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.getDataMovie().observe(this,{
            if (it != null){
                binding.progressBar.visibility = View.GONE
                binding.rvMovie.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                val adapter = AdapterFilm(it)
                binding.rvMovie.adapter = adapter
            }
        })
        viewModel.showList()

    }
}