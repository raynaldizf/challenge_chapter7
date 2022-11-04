package com.example.challenge_chapter6.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challenge_chapter6.databinding.ActivityDetailBinding
import com.example.challenge_chapter6.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        getDetailData()
        getDataObserve()

    }

//    fun getDetailData(){
//        val bun = intent.extras
//        val image = bun?.getString("gambar")
//
//        binding.nameMovie.text = bun?.getString("judul")
//        binding.txtDescription.text = bun?.getString("sinopsis")
//        binding.txtDate.text = bun?.getString("date")
//        binding.txtDirector.text = bun?.getString("director")
//        Glide.with(this).load(image).into(binding.imgMovie)
//    }

    fun getDataObserve(){
        val bun = intent.extras
        val id = bun?.getInt("id")
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.showDataMovieById(id!!)
        viewModel.getDataMovieById(id).observe(this,{
            binding.nameMovie.text = it!!.name
            binding.txtDescription.text = it!!.sinopsis
            binding.txtDate.text = it!!.date
            binding.txtDirector.text = it!!.director
            Glide.with(this).load(it!!.image).into(binding.imgMovie)
        })

    }

}