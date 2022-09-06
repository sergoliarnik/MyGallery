package com.example.mygallery.ui.photoList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygallery.data.model.Photos
import com.example.mygallery.data.repository.PhotosRepository
import kotlinx.coroutines.launch
import java.util.Collections.addAll

class PhotoListViewModel : ViewModel() {
    private var repository = PhotosRepository()
    val list: MutableLiveData<Photos> = MutableLiveData(Photos())

    var page = 1

    fun getPhotos(page: Int) {
        viewModelScope.launch {
            val response = repository.getPhotos(page).body()
            if(response != null){
                list.value?.let {
                    it.addAll(response)
                    list.value = list.value
                }
            }
            // To notify that data has changed
        }
    }
}