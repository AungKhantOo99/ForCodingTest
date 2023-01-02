package com.ako.taypad.Retrofit

import com.ako.taypad.Interface.JsonPlaceHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object testfilter {
    private val client = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
      // http://192.168.100.5:1337/api/categories
      // http://192.168.100.5:1337/api/stories?populate=%2A
      // http://192.168.100.5:1337/api/stories?populate=%2A
        .baseUrl("http://192.168.100.147:1337/api/")
        .client(client)
        .build()
    val JsonApi= retrofit.create(JsonPlaceHolder::class.java)
}