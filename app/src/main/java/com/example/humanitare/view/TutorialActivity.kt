package com.example.humanitare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.humanitare.R

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val btnCharityOrganizations = findViewById<Button>(R.id.btnCharityOrganizations)

        btnCharityOrganizations.setOnClickListener {
            val intent = Intent(this, CharityListActivity::class.java)
            startActivity(intent)
        }
    }
}