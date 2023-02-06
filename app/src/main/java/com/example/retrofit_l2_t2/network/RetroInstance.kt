package com.example.retrofit_l2_t2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {

    fun apiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://63e0c14ddd7041cafb3862b8.mockapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}