package com.example.challenge_chapter6.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_chapter6.databinding.ItemFavoriteBinding
import com.example.challenge_chapter6.model.ResponseFavoriteDataFilmItem

class AdapterFavorite(var listFavorite : List<ResponseFavoriteDataFilmItem>)  : RecyclerView.Adapter<AdapterFavorite.ViewHolder>(){
    var onClickDelete : ((ResponseFavoriteDataFilmItem) -> Unit)? = null
    class ViewHolder(var binding : ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.text = listFavorite[position].name
        holder.binding.description.text = listFavorite[position].director
        Glide.with(holder.itemView).load(listFavorite[position].image).into(holder.binding.imgMovie)

        holder.binding.btnDellete.setOnClickListener{
            onClickDelete?.invoke(listFavorite[position])
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

}

