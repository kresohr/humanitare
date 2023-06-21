package com.example.humanitare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.R
import com.example.humanitare.adapter.RecyclerViewAdapter
import com.example.humanitare.model.AccountTransactions
import com.example.humanitare.model.Transaction
import com.example.humanitare.source.remote.ApiClient
import com.example.humanitare.source.remote.ApiInterface
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // Organization wallets go here.
        val listOfOrganizationWallets = listOf("0x412a732c8688666de52092dab8fd7b2a5a03940d","0x0e57bdf109277cb6101932c8fa81ebe8e03548c1","0xbca47ae338c994eed941c78e74f4c431515d3875","0xcd77c325f71291f9a0a482fe58c1a3c5088d3d5a","0xa2acd30a8c1be2dc1fc62a547601c6a6e805b2e1")

        fun getWallet() {
            val sharedPreferences = getSharedPreferences("WalletAddress", MODE_PRIVATE)
            val walletValue = sharedPreferences.getString("wallet", "")
            if (!walletValue.isNullOrEmpty()) {
                txtWallet.text = walletValue
                btnSaveWallet.visibility = View.GONE
                imgEditWallet.visibility = View.VISIBLE
                txtWallet.isEnabled = false
                txtWalletLayout.hint = "Adresa novčanika kriptovaluta"
            }
            else {
                lblTransaction.visibility = View.GONE
                btnSaveWallet.visibility = View.VISIBLE
                txtWalletLayout.hint = "Unesite adresu novčanika kriptovaluta"
            }
        }

        getWallet()

        // API Call for transactions
        val endBlock = "99999999"
        val offset = "100" // The number of transactions fetched per page
        val page = "1"
        val sort = "desc"
        val startBlock = "0"

        if (!txtWallet.text.isNullOrEmpty()){
            val apiService: ApiInterface = ApiClient.getInstance().create(ApiInterface::class.java)
            val call: Call<AccountTransactions> = apiService.getAccountTransactions(address = txtWallet.text.toString(), endBlock = endBlock, offset = offset, page = page, sort = sort, startBlock = startBlock)

            call.enqueue(object : Callback<AccountTransactions> {
                override fun onResponse(
                    call: Call<AccountTransactions>,
                    response: Response<AccountTransactions>
                ) {
                    if (response.isSuccessful) {
                        val accountTransactions: AccountTransactions? = response.body()
                        val transactions: List<Transaction> = accountTransactions?.result ?: emptyList()
                        val listOfValidTransactions = mutableListOf<Transaction>()
                        transactions.filterTo(listOfValidTransactions) { transaction ->
                            val lowercaseTransactionTo = transaction.to.lowercase()
                            listOfOrganizationWallets.contains(lowercaseTransactionTo)
                        }

                        val adapter = RecyclerViewAdapter(listOfValidTransactions)
                        val layoutManager = LinearLayoutManager(this@ProfileActivity)
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    } else {
                        // Handle the API error
                    }
                }

                override fun onFailure(call: Call<AccountTransactions>, t: Throwable) {
                    // Handle the network failure
                }
            })
        }



        imgEditWallet.setOnClickListener {
            btnSaveWallet.visibility = View.VISIBLE
            imgEditWallet.visibility = View.GONE
            txtWallet.isEnabled = true
            lblTransaction.hint = "Unesite adresu novčanika kriptovaluta"
        }

        btnSaveWallet.setOnClickListener {
            val walletValue = txtWallet.text.toString()
            if (walletValue.length != 42){
                Toast.makeText(this, "Neispravan format adrese", Toast.LENGTH_SHORT).show()
            }
            else {
                val sharedPreferences = getSharedPreferences("WalletAddress", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("wallet", walletValue)
                editor.apply()
                Toast.makeText(this, "Uspješno pohranjena adresa", Toast.LENGTH_SHORT).show()
                btnSaveWallet.visibility = View.GONE
                txtWallet.clearFocus()
                getWallet()
            }

        }

        lblNoWallet.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }

    }
}