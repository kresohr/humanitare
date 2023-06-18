package com.example.humanitare.source.remote

import com.example.humanitare.model.MaticBalance
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api")
    fun getMaticBalance(
        @Query("module") module: String = "account",
        @Query("action") action: String = "balance",
        @Query("address") address: String,
        @Query("apikey") apiKey: String = "api_key_goes_here"
    ): Call<MaticBalance>
}