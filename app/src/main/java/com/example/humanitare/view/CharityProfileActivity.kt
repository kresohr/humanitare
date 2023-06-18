package com.example.humanitare.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.humanitare.R
import com.example.humanitare.model.MaticBalance
import com.example.humanitare.source.remote.ApiClient
import com.example.humanitare.source.remote.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharityProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_profile)
        val imgCharityLogo = findViewById<ImageView>(R.id.imgCharityProfileLogo)
        val lblCharityName = findViewById<TextView>(R.id.lblCharityProfileTitle)
        val lblCharityDescription = findViewById<TextView>(R.id.lblCharityProfileDescription)
        val txtCharityBalance = findViewById<TextView>(R.id.txtAccountBalance)
        val txtCharityTransactions = findViewById<TextView>(R.id.txtAccountTransactions)
        val btnDonate = findViewById<Button>(R.id.btnDonate)
        val btnTransactionHistory = findViewById<ImageView>(R.id.btnTransactionHistory)

        val organizationTitle = intent.getStringExtra("organizationTitle")
        val organizationDescription = intent.getStringExtra("organizationDescription")
        val organizationImageResId = intent.getIntExtra("organizationImageResId", 0)
        val organizationWalletAddress = intent.getStringExtra("organizationWalletAddress")

        imgCharityLogo.setImageResource(organizationImageResId)
        lblCharityName.text = organizationTitle
        lblCharityDescription.text = organizationDescription

        val apiService: ApiInterface = ApiClient.getInstance().create(ApiInterface::class.java)

        val call: Call<MaticBalance> = apiService.getMaticBalance(address = organizationWalletAddress.toString())
        call.enqueue(object : Callback<MaticBalance> {
            override fun onResponse(call: Call<MaticBalance>, response: Response<MaticBalance>) {
                if (response.isSuccessful) {
                    val balanceResponse: MaticBalance? = response.body()
                    val result = balanceResponse?.result

                    // Convert from "WEI" to Matic
                    val convertedMatic = result!!.toDouble() / 1e18
                    val formattedMatic = String.format("%.5f", convertedMatic)

                    if (result != null) {
                        txtCharityBalance.text = formattedMatic+" MATIC"
                        Log.d("SUCCESS", result)
                    }
                } else {
                    // Handle unsuccessful response
                    Log.d("ERROR", "Unsuccessful request.")
                }
            }
            override fun onFailure(call: Call<MaticBalance>, t: Throwable) {
                // Handle failure
            }
        })

        btnTransactionHistory.setOnClickListener {
            val explorerUrl = "https://polygonscan.com/address/"
            val url = explorerUrl + organizationWalletAddress

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

    }
}