package com.example.challenge_chapter6.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_chapter6.databinding.ActivityFavoriteBinding
import com.example.challenge_chapter6.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFavorite()
    }

    fun getDataFavorite(){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.getDataFavoriteMovie().observe(this,{
            if (it != null){
                binding.progressBar.visibility = View.GONE
                binding.rvMovie.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                val adapter = AdapterFavorite(it)
                binding.rvMovie.adapter = adapter
                adapter.onClickDelete = {
                    deleteFavorite(it.id.toInt())
                }
            }
        })
        viewModel.showListFavorite()
    }

    fun deleteFavorite(id : Int){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.deleteFavoriteMovie(id)
        viewModel.deleteDataFavoriteMovie().observe(this,{
            if (it != null){
                getDataFavorite()
                Toast.makeText(this, "Delete Favorite Success", Toast.LENGTH_LONG).show()
            }
        })
    }
}