package com.example.mygallery.data.repository


import com.example.mygallery.data.api.RetrofitInstance
import com.example.mygallery.data.model.Photos
import retrofit2.Response

class PhotosRepository {
    suspend fun getPhotos(page: Int): Response<Photos> {
        return RetrofitInstance.api.getPhotos(page)
    }
}