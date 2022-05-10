package com.example.ch18_network

import android.app.Application
import retrofit2.Retrofit

class MyApplication:Application() {
    companion object{
        var networkService : NetworkService
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl("https://api.odcloud.kr/api/")
                .build()
        init{
            networkService = retrofit.create(NetworkService::class.java)
        }
    }
}