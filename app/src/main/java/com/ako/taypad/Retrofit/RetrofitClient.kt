package com.ako.taypad.Retrofit

import com.ako.taypad.Interface.JsonPlaceHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
    //    http://192.168.100.212:1337/api/auth/local/register
        .baseUrl("http://192.168.100.212:1337/api/")
        .client(client)
        .build()
    val JsonApi = retrofit.create(JsonPlaceHolder::class.java)
}