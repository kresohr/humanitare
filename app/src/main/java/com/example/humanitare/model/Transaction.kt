package com.example.humanitare.model

data class Transaction(
    val blockNumber: String,
    val blockHash: String,
    val timeStamp: String,
    val hash: String,
    val nonce: String,
    val transactionIndex: String,
    val from: String,
    val to: String,
    val value: String,
    val gas: String,
    val gasPrice: String,
    val input: String,
    val contractAddress: String,
    val cumulativeGasUsed: String,
    val txReceiptStatus: String,
    val gasUsed: String,
    val confirmations: String,
    val isError: String
)