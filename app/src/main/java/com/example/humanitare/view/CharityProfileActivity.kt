package com.example.humanitare.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.example.humanitare.R
import com.example.humanitare.model.MaticBalance
import com.example.humanitare.model.MaticUSD
import com.example.humanitare.source.remote.ApiClient
import com.example.humanitare.source.remote.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class CharityProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_profile)
        var maticBalance = 0.0;
        val imgCharityLogo = findViewById<ImageView>(R.id.imgCharityProfileLogo)
        val lblCharityName = findViewById<TextView>(R.id.lblCharityProfileTitle)
        val lblCharityDescription = findViewById<TextView>(R.id.lblCharityProfileDescription)
        val txtCharityBalance = findViewById<TextView>(R.id.txtAccountBalance)
        val lblCharityAccountBalanceUSD = findViewById<TextView>(R.id.lblCharityAccountBalanceUSD)
        val imgLoading = findViewById<LottieAnimationView>(R.id.imgLoading)
        val btnDonate = findViewById<Button>(R.id.btnDonate)
        val btnTransactionHistory = findViewById<ImageView>(R.id.btnTransactionHistory)
        val txtError = findViewById<TextView>(R.id.txtError)
        val txtErrorReminder = findViewById<TextView>(R.id.txtErrorReminder)
        val btnFacebook = findViewById<ImageView>(R.id.imgFacebook)
        val btnWeb = findViewById<ImageView>(R.id.imgWeb)
        val btnCopyAddress = findViewById<Button>(R.id.btnCopyAddress)

        val organizationTitle = intent.getStringExtra("organizationTitle")
        val organizationDescription = intent.getStringExtra("organizationDescription")
        val organizationImageResId = intent.getIntExtra("organizationImageResId", 0)
        val organizationWalletAddress = intent.getStringExtra("organizationWalletAddress")
        val organizationFacebook = intent.getStringExtra("organizationFacebook")
        val organizationWebsite = intent.getStringExtra("organizationWebsite")



        imgCharityLogo.setImageResource(organizationImageResId)
        lblCharityName.text = organizationTitle
        lblCharityDescription.text = organizationDescription

        val apiService: ApiInterface = ApiClient.getInstance().create(ApiInterface::class.java)

        val call: Call<MaticBalance> =
            apiService.getMaticBalance(address = organizationWalletAddress.toString())
        call.enqueue(object : Callback<MaticBalance> {
            override fun onResponse(call: Call<MaticBalance>, response: Response<MaticBalance>) {
                if (response.isSuccessful) {
                    val balanceResponse: MaticBalance? = response.body()
                    val result = balanceResponse?.result

                    if (result != null && result != "Invalid API Key") {
                        // Convert from "WEI" to Matic
                        txtError.visibility = View.GONE
                        imgLoading.visibility = View.GONE
                        txtErrorReminder.visibility = View.GONE
                        txtCharityBalance.visibility = View.VISIBLE
                        val convertedMatic = result.toDouble() / 1e18
                        val formattedMatic = String.format("%.5f", convertedMatic)
                        txtCharityBalance.text = formattedMatic + " MATIC"
                        maticBalance = formattedMatic.toDouble()
                        Log.d("SUCCESS", result)

                        // Make API call for Matic USD
                        val callMaticUSD: Call<MaticUSD> = apiService.getMaticUSD()
                        callMaticUSD.enqueue(object : Callback<MaticUSD> {
                            override fun onResponse(call: Call<MaticUSD>, response: Response<MaticUSD>) {
                                if (response.isSuccessful) {
                                    val apiResponse: MaticUSD? = response.body()
                                    val maticUSDValue = apiResponse?.result?.maticusd
                                    // Use the Matic USD value as needed
                                    if (maticUSDValue != null) {
                                        Log.d("VRIJEDNOST", maticUSDValue)
                                        val decimalFormat = DecimalFormat("#.##")
                                        val balanceValue = decimalFormat.format(calculateBalance(maticBalance, maticUSDValue.toDouble()))
                                        lblCharityAccountBalanceUSD.text = "("+balanceValue.toString() + "$)"
                                    }
                                } else {
                                    // Handle unsuccessful response for Matic USD API call
                                    Log.d("VRIJEDNOST", "fail")
                                }
                            }

                            override fun onFailure(call: Call<MaticUSD>, t: Throwable) {
                                // Handle failure for Matic USD API call
                            }
                        })
                    } else {
                        txtError.visibility = View.VISIBLE
                        txtErrorReminder.visibility = View.VISIBLE
                        Log.d("REZULTAT", "Cannot fetch API, Wrong key?")
                    }
                } else {
                    Log.d("ERROR", "Unsuccessful request.")
                    txtError.visibility = View.VISIBLE
                    txtErrorReminder.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<MaticBalance>, t: Throwable) {
                // Handle unsuccessful response
                Log.d("ERROR", "Cannot reach API service.")
                txtError.visibility = View.VISIBLE
                txtErrorReminder.visibility = View.VISIBLE
            }
        })

//        val callMaticUSD: Call<MaticUSD> = apiService.getMaticUSD()
//        callMaticUSD.enqueue(object : Callback<MaticUSD> {
//            override fun onResponse(call: Call<MaticUSD>, response: Response<MaticUSD>) {
//                if (response.isSuccessful) {
//                    val apiResponse: MaticUSD? = response.body()
//                    val maticUSDValue = apiResponse?.result?.maticusd
//                    // Use the Matic USD value as needed
//                    if (maticUSDValue != null) {
//                        Log.d("VRIJEDNOST", maticUSDValue)
//                        lblCharityAccountBalanceUSD.text = calculateBalance(maticBalance,maticUSDValue.toDouble()).toString()
//
//                    }
//                } else {
//                    // Handle unsuccessful response for Matic USD API call
//                    Log.d("VRIJEDNOST", "fail")
//                }
//            }

//            override fun onFailure(call: Call<MaticUSD>, t: Throwable) {
//                // Handle failure for Matic USD API call
//            }
//        })

        btnTransactionHistory.setOnClickListener {
            val explorerUrl = "https://polygonscan.com/address/"
            val url = explorerUrl + organizationWalletAddress

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        btnFacebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(organizationFacebook)
            startActivity(intent)
        }

        btnWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(organizationWebsite)
            startActivity(intent)
        }

        btnDonate.setOnClickListener {
            val intent = Intent(this, DonateActivity::class.java)
            startActivity(intent)
        }

        btnCopyAddress.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("organi", organizationWalletAddress)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(this, "Adresa uspješno kopirana", Toast.LENGTH_SHORT).show()
        }

    }

    private fun calculateBalance(balance: Double,maticUSDValue: Double): Double {
        return balance*maticUSDValue
    }
}