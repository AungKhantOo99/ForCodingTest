package com.ako.taypad


import com.ako.taypad.model.*
import com.ako.taypad.model.ResponcePostData.ResponcePostData
import com.ako.taypad.responceImage.responceImagedata
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolder {
    @POST("register")
    fun signup(@Body userinfo: signupuser): Call<responseUser>

//    @GET("stories?populate=%2A")
//    fun get(@Header("Authorization")jwt:String): Call<Stories>

    @POST("categories")
    fun postdata(@Header("Authorization")jwt:String,@Body userinfo: PostCategory):Call<ResponcePostData>

    @Multipart
    @POST("upload")
    fun postfile(@Header("Authorization")jwt:String,
                 @Part files: MultipartBody.Part):Call<responceImagedata>
}