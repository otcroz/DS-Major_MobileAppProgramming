package com.example.mydjango

import android.app.Application
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object{
        var apiService: ApiService
        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://192.168.219.120:8000/")
                .addConverterFactory(GsonConverterFactory.create()) // 가져오려는 파일 형식: json
                .build()
        init{
            apiService = retrofit.create(ApiService::class.java)
        }
    }
}