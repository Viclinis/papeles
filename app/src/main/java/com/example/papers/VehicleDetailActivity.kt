package com.example.papers
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class VehicleDetailActivity : AppCompatActivity() {

    private lateinit var btnGoBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail)

        btnGoBack = findViewById(R.id.btnGoBackDetail)

        btnGoBack.setOnClickListener {
            finish()
        }
    }
}