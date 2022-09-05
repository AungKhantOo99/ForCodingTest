package com.ako.taypad.Retrofit

import com.ako.taypad.JsonPlaceHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRetrofit {
    private val client = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
    //    http://192.168.100.5:1337/api/auth/local/register
        .baseUrl("http://192.168.100.5:1337/api/auth/local/")
        .client(client)
        .build()
    val JsonApi = retrofit.create(JsonPlaceHolder::class.java)
}