package com.example.challenge_chapter6.viewmodel

import com.example.challenge_chapter6.model.ResponseDataFilmItem
import com.example.challenge_chapter6.model.ResponseFavoriteDataFilmItem
import com.example.challenge_chapter6.network.RetrofitService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ViewModelFilmTest{
    lateinit var service : RetrofitService
    @Before
    fun setUp(){
        service = mockk()
    }

    @Test
    fun testGetMovie():Unit = runBlocking {
        val responseGetMovie = mockk<Call<List<ResponseDataFilmItem>>>()

        every {
            runBlocking {
                service.getAllMvie()
            }
        } returns responseGetMovie
        val result = service.getAllMvie()

        verify {
            runBlocking {
                service.getAllMvie()
            }
        }
        assertEquals(responseGetMovie, result)
    }

    @Test
    fun deleteFavorite(): Unit{
        val responseDelete = mockk<Call<ResponseFavoriteDataFilmItem>>()

        every {
            service.deleteFavoriteMovie(1)
        }returns responseDelete

        val result = service.deleteFavoriteMovie(1)

        verify {
            service.deleteFavoriteMovie(1)
        }
        assertEquals(responseDelete, result)
    }
}

