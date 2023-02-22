package com.example.testproject.Retrofit

import com.example.testproject.Interface.JsonPlaceHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client = OkHttpClient.Builder().build()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://newsapi.org/v2/")
        .client(client)
        .build()
    val JsonApi = retrofit.create(JsonPlaceHolder::class.java)
}