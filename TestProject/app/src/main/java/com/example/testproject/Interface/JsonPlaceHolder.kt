package com.example.testproject.Interface

import com.example.testproject.model.members
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface JsonPlaceHolder {
    @GET("top-headlines?country=us&category=business&apiKey=3640d13ae22040dc9cc224be3be20743")
    fun getUsers(): Call<members>
}