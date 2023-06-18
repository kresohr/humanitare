package com.example.humanitare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.model.Transaction
import com.example.humanitare.R

class RecyclerViewAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<RecyclerViewAdapter.TransactionViewHolder>() {

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
            txtAmount.text = transaction.amount
            txtDate.text = transaction.date
        }

        private fun getOrganizationTitle(transaction: Transaction): String {
            return ""
        }
    }
}
