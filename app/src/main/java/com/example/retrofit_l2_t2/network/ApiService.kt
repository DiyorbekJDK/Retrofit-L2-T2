package com.example.retrofit_l2_t2.network

import com.example.retrofit_l2_t2.model.CardItem
import com.example.retrofit_l2_t2.model.CardResponse
import retrofit2.*
import retrofit2.http.*

interface ApiService {

    @GET("/api/v1/card")
    fun getCards(): Call<List<CardItem>>

    @GET("/api/v1/card/{id}")
    fun getUserById(@Path("id") id: Int): Call<CardResponse>

    @POST("/api/v1/card")
    fun addCard(@Body cardItem: CardItem): Call<CardResponse>

    @PUT("/api/v1/card/{id}")
    fun updateCard(@Path("id") id: Int,@Body cardItem: CardItem): Call<CardResponse>

    @DELETE("/api/v1/card/{id}")
    fun deleteCard(@Path("id") id: Int): Call<Any>


}