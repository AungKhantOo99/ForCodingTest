package com.ako.taypad.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ako.taypad.Retrofit.RetrofitClient
import com.ako.taypad.model.getallstories.allstories
import com.ako.taypad.model.mystories.mystories
import com.ako.taypad.model.storypartsdata.responsepartdata
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class AllData : ViewModel() {
    lateinit var alldata: MutableLiveData<allstories>
    lateinit var allPart:MutableLiveData<responsepartdata>
    lateinit var mystories:MutableLiveData<mystories>
    fun getdata(jwt:String) : LiveData<allstories>{
        if(! :: alldata.isInitialized){
            alldata= MutableLiveData()
            val getapi= RetrofitClient.JsonApi.allstories("Bearer $jwt")
            getapi.enqueue(object : Callback<allstories>{
                override fun onResponse(call: Call<allstories>,response: Response<allstories>)
                {
                    Log.d("Hello",response.body().toString())
                    alldata.value=response.body()
                }
                override fun onFailure(call: Call<allstories>, t: Throwable) {

                }
            })
        }
        return alldata
    }
    fun getmystories(jwt:String) : LiveData<mystories>{
        if(! :: mystories.isInitialized){
            mystories= MutableLiveData()
            val getapi= RetrofitClient.JsonApi.mystory("Bearer $jwt")
            getapi.enqueue(object : Callback<mystories>{
                override fun onResponse(call: Call<mystories>,response: Response<mystories>)
                {
                    Log.d("Hello",response.body().toString())
                    mystories.value=response.body()
                }
                override fun onFailure(call: Call<mystories>, t: Throwable) {

                }
            })
        }
        return mystories
    }
    fun allParts(jwt:String, position:Int):LiveData<responsepartdata>{
     //   if(! :: allPart.isInitialized){
            allPart= MutableLiveData()
            val call=RetrofitClient.JsonApi.getdata("Bearer $jwt",position.toString())
            call.enqueue(object :Callback<responsepartdata>{
                override fun onResponse(
                    call: Call<responsepartdata>,
                    response: Response<responsepartdata>
                ) {
                    if(response.code()==200){
                        allPart.value=response.body()
                    }
                }
                override fun onFailure(call: Call<responsepartdata>, t: Throwable) {
                }

            })
     //   }
        return allPart
    }
}