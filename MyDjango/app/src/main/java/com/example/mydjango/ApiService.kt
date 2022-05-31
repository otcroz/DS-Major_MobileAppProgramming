package com.example.mydjango

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("test")
    fun getList(
        @Query("format") returnType: String
    ): Call<MutableList<hInfoModel>>

    @POST("test/") // 반드시 슬래시 표시하기
    fun postData(
        @Body data: hInfoModel
    ): Call<hInfoModel>
}