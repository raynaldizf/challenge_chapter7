package com.example.challenge_chapter6.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_chapter6.databinding.ItemMovieBinding
import com.example.challenge_chapter6.model.ResponseDataFilmItem

class AdapterFilm(var dataFilm : List<ResponseDataFilmItem>) : RecyclerView.Adapter<AdapterFilm.ViewHolder>() {
    class ViewHolder(val binding : ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = dataFilm[position].name
        holder.binding.description.text = dataFilm[position].director
        Glide.with(holder.itemView).load(dataFilm[position].image).into(holder.binding.image)

        holder.binding.btnDetail.setOnClickListener{
            val bundle = Bundle()
            val inten = Intent(holder.itemView.context, DetailActivity::class.java)
            bundle.putString("judul", dataFilm[position].name)
            bundle.putString("date", dataFilm[position].date)
            bundle.putString("gambar", dataFilm[position].image)
            bundle.putString("director",dataFilm[position].director)
            bundle.putString("sinopsis", dataFilm[position].sinopsis)
            bundle.putInt("id", dataFilm[position].id.toInt())
            inten.putExtras(bundle)
            holder.itemView.context.startActivity(inten)
        }

        holder.binding.btnAddToFavorite.setOnClickListener{

            val bundle = Bundle()
            val inten = Intent(holder.itemView.context, AddFavoriteActivity::class.java)
            bundle.putString("judul", dataFilm[position].name)
            bundle.putString("date", dataFilm[position].date)
            bundle.putString("gambar", dataFilm[position].image)
            bundle.putString("director",dataFilm[position].director)
            bundle.putString("sinopsis", dataFilm[position].sinopsis)
            bundle.putInt("id", dataFilm[position].id.toInt())
            inten.putExtras(bundle)
            holder.itemView.context.startActivity(inten)
        }

    }

    override fun getItemCount(): Int {
        return dataFilm.size
    }

}