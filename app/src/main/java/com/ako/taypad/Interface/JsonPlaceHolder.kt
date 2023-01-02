package com.ako.taypad.Interface


import com.ako.taypad.model.*
import com.ako.taypad.model.comment.commentdata
import com.ako.taypad.model.comment.commetresponce
import com.ako.taypad.model.getallstories.allstories
import com.ako.taypad.model.getpartsdata.getpartsdata
import com.ako.taypad.model.like.likeresponce
import com.ako.taypad.model.mystories.mystories
import com.ako.taypad.model.responceImage.responceImagedata
import com.ako.taypad.model.sendstorydata.getdata
import com.ako.taypad.model.sendstorydata.postdata
import com.ako.taypad.model.skipaction.responsedata
import com.ako.taypad.model.skipaction.skipdata
import com.ako.taypad.model.storypartsdata.responsepartdata
import com.ako.taypad.model.writeparts.publicparts.publicpartsdata
import com.ako.taypad.model.writeparts.responseparts.responsepartsdata
import com.ako.taypad.model.writeparts.savedriftparts.savedriftpartsdata
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface JsonPlaceHolder {

    @GET("stories/me")
    fun mystory(@Header("Authorization")jwt: String):Call<mystories>

    @POST("auth/local/register")
    fun signup(@Body userinfo: signupuser): Call<responseUser>

    @POST("auth/local")
    fun login(@Body userinfo: loginuser): Call<responseUser>

    @GET("users/me")
    fun getprofiledata(@Header("Authorization")jwt:String): Call<profiledata>

    @GET("users/{user_id}")
    fun getotherprofiledata(@Header("Authorization")jwt:String,@Path(value = "user_id", encoded = true)userId:String): Call<profiledata>

    @GET("read-the-parts/{user_id}")
    fun getdata( @Header("Authorization")jwt:String,@Path(value = "user_id", encoded = true)userId:String):Call<responsepartdata>

    @Multipart
    @POST("upload")
    fun postfile(@Header("Authorization")jwt:String,
                 @Part files: MultipartBody.Part):Call<responceImagedata>

    @POST("stories")
    fun skipAction(@Header("Authorization")jwt:String, @Body data: skipdata):Call<responsedata>

    @POST("stories")
    fun poststories(@Header("Authorization")jwt:String,@Body data: postdata):Call<getdata>

    @POST("parts")
    fun savesadrift(@Header("Authorization")jwt:String,@Body data: savedriftpartsdata):Call<responsepartsdata>

    @POST("parts")
    fun publicparts(@Header("Authorization")jwt:String,@Body data: publicpartsdata):Call<responsepartsdata>

    @POST("like-the-parts")
    fun likepart( @Header("Authorization")jwt:String,
                  @Query("part")id:String)
                 :Call<likeresponce>

    @POST("comment-the-parts")
    fun comment(@Header("Authorization")jwt:String,@Body data: commentdata):Call<commetresponce>

    @GET("stories/{user_id}?populate=%2A")
    fun getparts( @Header("Authorization")jwt:String,@Path(value = "user_id", encoded = true)userId:String):Call<getpartsdata>

    @GET("stories")
    fun allstories(@Header("Authorization")jwt:String):Call<allstories>
}