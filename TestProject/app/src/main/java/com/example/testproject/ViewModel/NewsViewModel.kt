package com.example.testproject.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testproject.Retrofit.RetrofitClient
import com.example.testproject.model.members
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel :ViewModel(){
    lateinit var NewsData: MutableLiveData<members>
    fun getnewsdata() : LiveData<members> {
        if(! :: NewsData.isInitialized){
            NewsData= MutableLiveData()
            val getapi= RetrofitClient.JsonApi.getUsers()
            getapi.enqueue(object : Callback<members> {
                override fun onResponse(call: Call<members>, response: Response<members>)
                {
                    Log.d("Hello",response.body().toString())
                    NewsData.value=response.body()
                }
                override fun onFailure(call: Call<members>, t: Throwable) {

                }
            })
        }
        return NewsData
    }

}