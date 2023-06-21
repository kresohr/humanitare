package com.example.humanitare.model

data class AccountTransactions(
    val status: String,
    val message: String,
    val result: List<Transaction>
)
