package com.example.humanitare.model

data class Organization(
    var title: String,
    val description: String,
    val imageResId: Int,
    val wallet: String,
    val facebook: String,
    val website: String)
