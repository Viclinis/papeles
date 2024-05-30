package com.example.papers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        val etPlate = findViewById<EditText>(R.id.etPlate)
        val etModel = findViewById<EditText>(R.id.etModel)
        val etYear = findViewById<EditText>(R.id.etYear)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val plate = etPlate.text.toString()
            val model = etModel.text.toString()
            val year = etYear.text.toString()

            // Handle data submission (e.g., save to database or send to server)
        }
    }
}