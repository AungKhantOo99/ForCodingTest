package com.ako.taypad.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ako.taypad.model.storypartsdata.responsepartdata
import com.ako.taypad.Retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartsDateRepository:PartsDateRepositoryClient {
    private val livedata=MutableLiveData<responsepartdata>()
     override fun getEpisodes(jwt: String,id: String): LiveData<responsepartdata> {
     RetrofitClient.JsonApi.getdata("Bearer $jwt",id).enqueue(object :Callback<responsepartdata>{
         override fun onResponse(call: Call<responsepartdata>, response: Response<responsepartdata>) {
             livedata.value=response.body()
         }

         override fun onFailure(call: Call<responsepartdata>, t: Throwable) {

         }

     })
         return livedata
    }

}

interface PartsDateRepositoryClient{
    fun getEpisodes(jwt:String,id:String): LiveData<responsepartdata>
}