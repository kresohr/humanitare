package com.example.humanitare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.model.Transaction
import com.example.humanitare.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class RecyclerViewAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<RecyclerViewAdapter.TransactionViewHolder>() {

    private val organizationMap = mapOf(
        "0x412a732c8688666de52092dab8fd7b2a5a03940d" to "Fajter",
        "0x0e57bdf109277cb6101932c8fa81ebe8e03548c1" to "Noina Arka",
        "0xbca47ae338c994eed941c78e74f4c431515d3875" to "Savao",
        "0xcd77c325f71291f9a0a482fe58c1a3c5088d3d5a" to "SOS Djecje Selo",
        "0xa2acd30a8c1be2dc1fc62a547601c6a6e805b2e1" to "Srce Split"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtOrganization: TextView = itemView.findViewById(R.id.txtOrganization)
        private val txtAmount: TextView = itemView.findViewById(R.id.txtAmount)
        private val txtDate: TextView = itemView.findViewById(R.id.txtDate)

        fun bind(transaction: Transaction) {
            txtOrganization.text = getOrganizationTitle(transaction)
            txtAmount.text = convertWeiToMatic(transaction.value.toDouble())
            txtDate.text = convertTimestampToFormattedDate(transaction.timeStamp.toLong())
        }

        private fun convertTimestampToFormattedDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
            val date = Date(timestamp * 1000) // Multiply by 1000 to convert seconds to milliseconds
            return sdf.format(date)
        }

        private fun convertWeiToMatic(wei: Double): String {
            val convertedMatic = wei / 1e18
            val formattedMatic = String.format("%.5f", convertedMatic)
            val decimalFormat = DecimalFormat("#.###")
            return decimalFormat.format(formattedMatic.toDouble())
        }

        private fun getOrganizationTitle(transaction: Transaction): String {
            val organization = organizationMap[transaction.to.lowercase()]
            return organization ?: ""
        }
    }
}
