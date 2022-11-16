package com.naveen.retrofitlibrary

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/posts")
    fun getAllPosts() : Call<List<Posts>>
}