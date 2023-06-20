package com.example.humanitare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.humanitare.R

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_landing)

        // Check if it's first launch, otherwise launch CharityListActivity
        val sharedPreferences = getSharedPreferences("isFirstLaunch", MODE_PRIVATE)
//        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        val isFirstLaunch = true

        if (isFirstLaunch) {
            setContentView(R.layout.activity_landing)
            val txtWelcomeWalletInput = findViewById<TextView>(R.id.txtWelcomeWalletInput)
            val lblWelcomeNoWallet = findViewById<TextView>(R.id.lblWelcomeNoWallet)
            val btnLandingSaveWallet = findViewById<Button>(R.id.btnLandingSaveWallet)
            val btnSkip = findViewById<Button>(R.id.btnSkip)

            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstLaunch", false)
            editor.apply()

            btnLandingSaveWallet.setOnClickListener {
                val walletInput = txtWelcomeWalletInput.text.toString()

                val sharedPreferences = getSharedPreferences("WalletAddress", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("wallet", walletInput)
                editor.apply()

                Toast.makeText(this, "Adresa uspješno pohranjena", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CharityListActivity::class.java))
                finish()
            }

            btnSkip.setOnClickListener {
                startActivity(Intent(this, CharityListActivity::class.java))
                finish()
            }
        } else {
            startActivity(Intent(this, CharityListActivity::class.java))
            finish()
        }
    }
}