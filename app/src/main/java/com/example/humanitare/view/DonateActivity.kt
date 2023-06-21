package com.example.humanitare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.humanitare.R

class DonateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        val btnInfo = findViewById<Button>(R.id.btnInfo)

        btnInfo.setOnClickListener {
            startActivity(Intent(this, TutorialActivity::class.java))
        }
    }
}