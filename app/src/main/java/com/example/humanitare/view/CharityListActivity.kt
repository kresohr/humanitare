package com.example.humanitare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.humanitare.R
import com.example.humanitare.adapter.ViewPagerAdapter
import com.example.humanitare.model.Organization

class CharityListActivity : AppCompatActivity(), ViewPagerAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity_list)

        val btnProfile = findViewById<Button>(R.id.btnCharityProfile)
        val arrowImgRight = findViewById<ImageView>(R.id.imgArrowRight)
        val arrowImgLeft = findViewById<ImageView>(R.id.imgArrowLeft)
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPagerOrganizations)
        val adapter = ViewPagerAdapter(ViewPagerAdapter.create(),this)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == adapter.itemCount - 1) {
                    arrowImgRight.visibility = View.GONE
                }
                else {
                    arrowImgRight.visibility = View.VISIBLE
                }
                if (position == 0) {
                    arrowImgLeft.visibility = View.GONE
                }
                else {
                    arrowImgLeft.visibility = View.VISIBLE
                }
            }
        })

        arrowImgRight.setOnClickListener {
            // Increment the current item of the ViewPager2 by 1
            val currentItem = viewPager2.currentItem
            val nextItem = currentItem + 1
            if (nextItem < adapter.itemCount) {
                viewPager2.currentItem = nextItem
            }
        }

        arrowImgLeft.setOnClickListener {
            val currentPosition = viewPager2.currentItem
            val previousPosition = currentPosition - 1
            if (previousPosition >= 0) {
                viewPager2.currentItem = previousPosition
            }
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(organization: Organization) {
        val intent = Intent(this, CharityProfileActivity::class.java).apply {
            putExtra("organizationTitle", organization.title)
            putExtra("organizationDescription", organization.description)
            putExtra("organizationImageResId", organization.imageResId)
            putExtra("organizationWalletAddress", organization.wallet)
            putExtra("organizationFacebook", organization.facebook)
            putExtra("organizationWebsite", organization.website)
        }
        startActivity(intent)
    }
}