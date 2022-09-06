package com.example.mygallery.data.api


import com.example.mygallery.data.model.Photos
import com.example.mygallery.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos/?client_id=$API_KEY")
    suspend fun getPhotos(@Query("page") page: Int): Response<Photos>

}