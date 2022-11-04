package com.example.challenge_chapter6.model


import com.google.gson.annotations.SerializedName

data class ResponseFavoriteDataFilmItem(
    @SerializedName("date")
    val date: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("sinopsis")
    val sinopsis: String
)