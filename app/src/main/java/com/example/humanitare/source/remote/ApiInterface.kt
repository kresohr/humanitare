package com.example.humanitare.source.remote

import com.example.humanitare.model.AccountTransactions
import com.example.humanitare.model.MaticBalance
import com.example.humanitare.model.MaticUSD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api")
    fun getMaticBalance(
        @Query("module") module: String = "account",
        @Query("action") action: String = "balance",
        @Query("address") address: String,
        @Query("apikey") apiKey: String = "api_key"
    ): Call<MaticBalance>

    @GET("/api")
    fun getMaticUSD(
        @Query("module") module: String = "stats",
        @Query("action") action: String = "maticprice",
        @Query("apikey") apiKey: String = "api_key"
    ): Call<MaticUSD>

    @GET("/api")
    fun getAccountTransactions(
        @Query("module") module: String = "account",
        @Query("action") action: String = "txlist",
        @Query("address") address: String,
        @Query("startblock") startBlock: String,
        @Query("endblock") endBlock: String,
        @Query("page") page: String,
        @Query("offset") offset: String,
        @Query("sort") sort: String,
        @Query("apikey") apiKey: String = "api_key"
    ): Call<AccountTransactions>
}