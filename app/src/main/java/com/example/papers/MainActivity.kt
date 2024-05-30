package com.example.papers

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import com.google.android.material.floatingactionbutton.FloatingActionButton

import android.app.Activity
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        fab.setOnClickListener {
            val intent = Intent(this, AddDocumentActivity::class.java)
            startActivity(intent)
        }
    }
}