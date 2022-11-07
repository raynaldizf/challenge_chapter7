package com.example.challenge_chapter6.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.challenge_chapter6.databinding.ActivityAddFavoriteBinding
import com.example.challenge_chapter6.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFavoriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataObserve()

        binding.btnTambah.setOnClickListener{
            val name = binding.etJudul.text.toString()
            val sinopsis = binding.etSinopsis.text.toString()
            val date = binding.etTanggalRilis.text.toString()
            val director = binding.etDirector.text.toString()
            val image = binding.etLinkImage.text.toString()

            addToFavorite(sinopsis,director,date,image,name)
            val inten = Intent(this, FavoriteActivity::class.java)
            startActivity(inten)

        }




//        getDetailData()
    }

    fun getDetailData(){
        val bun = intent.extras
        val image = bun?.getString("gambar")
        val name = bun?.getString("judul")
        val sinopsis = bun?.getString("sinopsis")
        val director = bun?.getString("director")
        val date = bun?.getString("date")

        binding.etJudul.setText(name).toString()
        binding.etSinopsis.setText(sinopsis).toString()
        binding.etDirector.setText(director).toString()
        binding.etTanggalRilis.setText(date).toString()
        binding.etLinkImage.setText(image).toString()

//        addToFavorite(sinopsis,director,date,image,name)



    }

    fun getDataObserve(){
        val bun = intent.extras
        val id = bun?.getInt("id")
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.showDataMovieById(id!!)
        viewModel.getDataMovieById(id).observe(this,{
            binding.etJudul.setText(it!!.name)
            binding.etSinopsis.setText(it!!.sinopsis)
            binding.etDirector.setText(it!!.director)
            binding.etTanggalRilis.setText(it!!.date)
            binding.etLinkImage.setText(it!!.image)
        })

    }


    fun addToFavorite(sinopsis : String,director : String,date : String,image : String,name : String){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.addFavoriteMovie(sinopsis,director,date,image,name)
        viewModel.addDataFavoriteMovie().observe(this,{
            Toast.makeText(this, "Add Favorite Success", Toast.LENGTH_LONG).show()
        })
        finish()
    }
}