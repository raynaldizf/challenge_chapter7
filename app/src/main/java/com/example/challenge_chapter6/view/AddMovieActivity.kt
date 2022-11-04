package com.example.challenge_chapter6.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.challenge_chapter6.MainActivity
import com.example.challenge_chapter6.databinding.ActivityAddMovieBinding
import com.example.challenge_chapter6.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMovieActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener{
            val name = binding.etJudul.text.toString()
            val date = binding.etTanggalRilis.text.toString()
            val director = binding.etDirector.text.toString()
            val sinopsis = binding.etSinopsis.text.toString()
            val image = binding.etLinkImage.text.toString()

            addMovie(sinopsis,director,date, image,name )
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
            Toast.makeText(this, "Add Data Success", Toast.LENGTH_LONG).show()
        }
    }
    fun addMovie(sinopsis : String,director : String,date : String,image : String,name : String){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.postDataMovie(sinopsis,director,date,image,name)
        viewModel.addDataMovie().observe(this,{
            if (it != null){
                Toast.makeText(this, "Add Data Success", Toast.LENGTH_LONG).show()
            }
        })
        finish()
    }
}