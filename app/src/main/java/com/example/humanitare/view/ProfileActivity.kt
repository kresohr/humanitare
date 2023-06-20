package com.example.humanitare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.R
import com.google.android.material.textfield.TextInputLayout

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnSaveWallet = findViewById<Button>(R.id.btnSaveWallet)
        val txtWallet = findViewById<TextView>(R.id.txtProfileWalletInput)
        val txtWalletLayout = findViewById<TextInputLayout>(R.id.txtProfileWalletLayout)
        val lblNoWallet = findViewById<TextView>(R.id.lblProfileNoWallet)
        val imgEditWallet = findViewById<ImageView>(R.id.imgEditWallet)
        val lblTransaction = findViewById<TextView>(R.id.lblTransactions)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTransactions)

        fun getWallet() {
            val sharedPreferences = getSharedPreferences("WalletAddress", MODE_PRIVATE)
            val walletValue = sharedPreferences.getString("wallet", "")
            if (!walletValue.isNullOrEmpty()) {
                txtWallet.text = walletValue
                btnSaveWallet.visibility = View.GONE
                imgEditWallet.visibility = View.VISIBLE
                txtWallet.isEnabled = false
                txtWalletLayout.hint = "Wallet address"
            }
            else {
                lblTransaction.visibility = View.GONE
                btnSaveWallet.visibility = View.VISIBLE
                txtWalletLayout.hint = "Enter wallet address"
            }
        }

        getWallet()

        imgEditWallet.setOnClickListener {
            btnSaveWallet.visibility = View.VISIBLE
            imgEditWallet.visibility = View.GONE
            txtWallet.isEnabled = true
            lblTransaction.hint = "Enter wallet address"
        }

        btnSaveWallet.setOnClickListener {
            val walletValue = txtWallet.text.toString()
            val sharedPreferences = getSharedPreferences("WalletAddress", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("wallet", walletValue)
            editor.apply()
            Toast.makeText(this, "Uspješno pohranjena adresa", Toast.LENGTH_SHORT).show()
            btnSaveWallet.visibility = View.GONE
            txtWallet.clearFocus()
            getWallet()
        }

        lblNoWallet.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }

    }
}