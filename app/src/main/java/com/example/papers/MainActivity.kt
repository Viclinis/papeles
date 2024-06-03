package com.example.papers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vehicleAdapter: VehicleAdapter

    companion object {
        private const val REQUEST_READ_EXTERNAL_STORAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Verificar y solicitar permisos
        checkPermissions()

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        val fabDetail = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fab.setOnClickListener {
            val intent = Intent(this, AddDocumentActivity::class.java)
            startActivity(intent)
        }

        fabDetail.setOnClickListener {
            val intent = Intent(this, VehicleDetailActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadDAtaFromSharedPreferences()
    }

    private fun loadDAtaFromSharedPreferences() {
        val sharedPref = getSharedPreferences("com.example.papers.PREFERENCES", Context.MODE_PRIVATE)
        val imageUri = sharedPref.getString("imageUri", null)
        val documentSoat = sharedPref.getString("documentSoat", null)
        val documentCDA = sharedPref.getString("documentCDA", null)
        val vehicleType = sharedPref.getString("vehicleType", null)
        val plate = sharedPref.getString("plate", null)
        val brand = sharedPref.getString("brand", null)
        val model = sharedPref.getString("model", null)

        // Check if any of the data is null or empty
        if (!imageUri.isNullOrEmpty() && !documentSoat.isNullOrEmpty() && !documentCDA.isNullOrEmpty() && !vehicleType.isNullOrEmpty() && !plate.isNullOrEmpty() && !brand.isNullOrEmpty() && !model.isNullOrEmpty()) {
            // Populate the RecyclerView with this data
            val vehicleData = AddDocumentActivity().apply {
                this.imageUri = Uri.parse(imageUri)
                this.documentSoat = Uri.parse(documentSoat)
                this.documentCDA = Uri.parse(documentCDA)
                this.vehicleType = vehicleType
                this.plate = plate
                this.brand = brand
                this.model = model
            }

            vehicleAdapter = VehicleAdapter(this, listOf(vehicleData))
            recyclerView.adapter = vehicleAdapter
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
        }
    }
}
