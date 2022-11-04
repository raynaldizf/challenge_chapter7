package com.example.challenge_chapter6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_chapter6.model.*
import com.example.challenge_chapter6.network.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.sin

@HiltViewModel
class ViewModelFilm @Inject constructor(val api : RetrofitService) : ViewModel() {
    lateinit var getDataMovie: MutableLiveData<List<ResponseDataFilmItem>?>
    lateinit var addDataMovie: MutableLiveData<ResponseDataFilm?>
    lateinit var getDataMovieById: MutableLiveData<ResponseDataFilmItem?>


    lateinit var getDataFavoriteMovie: MutableLiveData<List<ResponseFavoriteDataFilmItem>?>
    lateinit var addDataFavoriteMovie: MutableLiveData<ResponseFavoriteDataFilm?>
    lateinit var deleteDataFavoriteMovie: MutableLiveData<ResponseFavoriteDataFilmItem?>


    init {
        getDataMovie = MutableLiveData()
        addDataMovie = MutableLiveData()
        getDataMovieById = MutableLiveData()



        getDataFavoriteMovie = MutableLiveData()
        addDataFavoriteMovie = MutableLiveData()
        deleteDataFavoriteMovie = MutableLiveData()


    }

    fun getDataMovie(): MutableLiveData<List<ResponseDataFilmItem>?> {
        return getDataMovie
    }

    fun getDataMovieById(id : Int): MutableLiveData<ResponseDataFilmItem?> {
        return getDataMovieById
    }

    fun addDataMovie(): MutableLiveData<ResponseDataFilm?> {
        return addDataMovie
    }


    fun getDataFavoriteMovie(): MutableLiveData<List<ResponseFavoriteDataFilmItem>?> {
        return getDataFavoriteMovie
    }

    fun addDataFavoriteMovie(): MutableLiveData<ResponseFavoriteDataFilm?> {
        return addDataFavoriteMovie
    }

    fun deleteDataFavoriteMovie(): MutableLiveData<ResponseFavoriteDataFilmItem?> {
        return deleteDataFavoriteMovie
    }


    fun showList() {
        api.getAllMvie()
            .enqueue(object : retrofit2.Callback<List<ResponseDataFilmItem>> {
                override fun onResponse(
                    call: retrofit2.Call<List<ResponseDataFilmItem>>,
                    response: retrofit2.Response<List<ResponseDataFilmItem>>,
                ) {
                    if (response.isSuccessful) {
                        getDataMovie.postValue(response.body())
                    } else {
                        getDataMovie.postValue(null)
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<List<ResponseDataFilmItem>>,
                    t: Throwable
                ) {
                    getDataMovie.postValue(null)
                }

            })
    }

    fun showDataMovieById(id: Int) {
        api.getMovieById(id)
            .enqueue(object : retrofit2.Callback<ResponseDataFilmItem> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseDataFilmItem>,
                    response: retrofit2.Response<ResponseDataFilmItem>,
                ) {
                    if (response.isSuccessful) {
                        getDataMovieById.postValue(response.body())
                    } else {
                        getDataMovieById.postValue(null)
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<ResponseDataFilmItem>,
                    t: Throwable
                ) {
                    getDataMovieById.postValue(null)
                }

            })
    }

    fun postDataMovie(
        name: String,
        director: String,
        date: String,
        image: String,
        sinopsis: String
    ) {
        api.addMovie(DataFilm(name, director, date, image, sinopsis))
            .enqueue(object : Callback<ResponseDataFilm> {
                override fun onResponse(
                    call: Call<ResponseDataFilm>,
                    response: Response<ResponseDataFilm>,
                ) {
                    if (response.isSuccessful) {
                        addDataMovie.postValue(response.body())
                    } else {
                        addDataMovie.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<ResponseDataFilm>, t: Throwable) {
                    addDataMovie.postValue(null)
                }

            })
    }

    fun showListFavorite() {
        api.getFavoriteMovie()
            .enqueue(object : Callback<List<ResponseFavoriteDataFilmItem>> {
                override fun onResponse(
                    call: Call<List<ResponseFavoriteDataFilmItem>>,
                    response: Response<List<ResponseFavoriteDataFilmItem>>,
                ) {
                    if (response.isSuccessful) {
                        getDataFavoriteMovie.postValue(response.body())
                    } else {
                        getDataFavoriteMovie.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<List<ResponseFavoriteDataFilmItem>>,
                    t: Throwable
                ) {
                    getDataFavoriteMovie.postValue(null)
                }

            })
    }

    fun deleteFavoriteMovie(id: Int) {
        api.deleteFavoriteMovie(id)
            .enqueue(object : Callback<ResponseFavoriteDataFilmItem> {
                override fun onResponse(
                    call: Call<ResponseFavoriteDataFilmItem>,
                    response: Response<ResponseFavoriteDataFilmItem>,
                ) {
                    if (response.isSuccessful) {
                        deleteDataFavoriteMovie.postValue(response.body())
                    } else {
                        deleteDataFavoriteMovie.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<ResponseFavoriteDataFilmItem>,
                    t: Throwable
                ) {
                    deleteDataFavoriteMovie.postValue(null)
                }

            })
    }

    fun addFavoriteMovie(sinopsis : String,director : String,date : String,image : String,name : String){
        api.addFavoriteMovie(DataFilm(sinopsis,director,date,image,name))
            .enqueue(object : Callback<ResponseFavoriteDataFilm>{
                override fun onResponse(
                    call: Call<ResponseFavoriteDataFilm>,
                    response: Response<ResponseFavoriteDataFilm>
                ) {
                    if (response.isSuccessful){
                        addDataFavoriteMovie.postValue(response.body())
                    }else{
                        addDataFavoriteMovie.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseFavoriteDataFilm>, t: Throwable) {
                    addDataFavoriteMovie.postValue(null)
                }

            })
    }


}