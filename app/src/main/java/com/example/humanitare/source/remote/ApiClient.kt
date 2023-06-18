package com.example.humanitare.source.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.polygonscan.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}