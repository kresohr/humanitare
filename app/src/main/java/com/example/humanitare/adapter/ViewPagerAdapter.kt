package com.example.humanitare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.humanitare.R
import com.example.humanitare.model.Organization

class ViewPagerAdapter(private val mockDataList: List<Organization>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ImgOrganizationIcon = itemView.findViewById<ImageView>(R.id.ImgOrganizationIcon)
        private val lblOrganizationTitle = itemView.findViewById<TextView>(R.id.lblOrganizationTitle)
        private val lblOrganizationDescription = itemView.findViewById<TextView>(R.id.lblOrganizationDescription)

        fun bind(mockData: Organization) {
            ImgOrganizationIcon.setImageResource(mockData.imageResId)
            lblOrganizationTitle.text = mockData.title
            lblOrganizationDescription.text = mockData.description
        }
    }

    //Temporary mock data for debugging purposes
    companion object {
        private val mockDataList = listOf(
            Organization("Title 1", "Description 1", R.drawable.medicine),
            Organization("Title 2", "Description 2", R.drawable.medicine),
            Organization("Title 3", "Description 3", R.drawable.medicine)
        )

        fun create() = ViewPagerAdapter(mockDataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mockDataList[position])
    }

    override fun getItemCount() = mockDataList.size

}