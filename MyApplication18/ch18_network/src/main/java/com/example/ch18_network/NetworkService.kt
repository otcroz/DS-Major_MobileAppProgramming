package com.example.ch18_network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("apnmOrg/v1/list") // 앞에 있는 슬래시 제거
    fun getList(
        @Query("page") page: Long,
        @Query("perPage") perPage: Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") apiKey: String
    ) : Call<PageListModel>

    @GET("B551182/pubReliefHospService/getpubReliefHospList")
    fun getXmlList(
        @Query("serviceKey") apiKey: String?,
        @Query("pageNo") page:Int,
        @Query("numOfRows") pageSize: Int
    ) : Call<responseInfo>
}