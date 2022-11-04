package com.example.challenge_chapter6.network

import com.example.challenge_chapter6.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("movie")
    fun getAllMvie() : Call<List<ResponseDataFilmItem>>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id : Int): Call<ResponseDataFilmItem>

    @POST("movie")
    fun addMovie(@Body request : DataFilm): Call<ResponseDataFilm>


    @GET("favorite")
    fun getFavoriteMovie() : Call<List<ResponseFavoriteDataFilmItem>>

    @POST("favorite")
    fun addFavoriteMovie(@Body request : DataFilm): Call<ResponseFavoriteDataFilm>

    @DELETE("favorite/{id}")
    fun deleteFavoriteMovie(@Path("id") id : Int): Call<ResponseFavoriteDataFilmItem>
}