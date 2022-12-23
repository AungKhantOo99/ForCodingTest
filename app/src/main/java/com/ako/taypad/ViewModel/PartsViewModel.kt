package com.ako.taypad.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ako.taypad.model.storypartsdata.responsepartdata
import com.ako.taypad.repository.PartsDateRepositoryClient

class PartsViewModel(private val client: PartsDateRepositoryClient):ViewModel() {
    fun episodesLiveData(jwt:String,id:String): LiveData<responsepartdata> =  client.getEpisodes(jwt,id)
}