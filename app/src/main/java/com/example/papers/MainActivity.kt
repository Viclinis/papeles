package com.example.papers

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vehicleAdapter: VehicleAdapter
    private val vehicles = mutableListOf<Vehicle>() //Lista de vehiculos

    private val ADD_VEHICLE_REQUEST = 1

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
            //startActivity(intent)
            startActivityForResult(intent, ADD_VEHICLE_REQUEST)
        }

        fabDetail.setOnClickListener {
            Log.d("MainActivity", "fabDetail clicked: $vehicles")
            println("------ Se presiono ------")
            println("metodo presionado ")
            println("------ vehicles: " + vehicles)

            if (vehicles.isNotEmpty()) {
                val selectedVehicle = vehicles.last() // Selecciona el último vehículo agregado (puedes cambiar esta lógica)
                Log.d("MainActivity", "Vehicle data: $selectedVehicle")
                println("------------------------------------------------------------------------------")
                println("Vehicle data: $selectedVehicle")
                val intent = Intent(this, VehicleDetailActivity::class.java).apply {
                    putExtra("imageUri", selectedVehicle.imageUri)
                    putExtra("vehicleType", selectedVehicle.vehicleType)
                    putExtra("plate", selectedVehicle.plate)
                }
                startActivity(intent)
            } else {
                Log.d("MainActivity", "No vehicles available.")
                println("No vehicles available.")
            }
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        vehicleAdapter = VehicleAdapter(this, vehicles)
        recyclerView.adapter = vehicleAdapter
        loadDAtaFromSharedPreferences()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == ADD_VEHICLE_REQUEST && data != null) {
            val imageUri = data.getStringExtra("imageUri")
            val documentSoat = data.getStringExtra("documentSoat")
            val documentCDA = data.getStringExtra("documentCDA")
            val vehicleType = data.getStringExtra("vehicleType")
            val plate = data.getStringExtra("plate")
            val brand = data.getStringExtra("brand")
            val model = data.getStringExtra("model")

            if (imageUri != null && vehicleType != null && plate != null && brand != null && model != null) {
                val vehicle = Vehicle(imageUri, plate, vehicleType, brand, model)
                vehicles.add(vehicle)
                vehicleAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun loadDAtaFromSharedPreferences(): List<Vehicle> {
        val sharedPref = getSharedPreferences("com.example.papers.PREFERENCES", Context.MODE_PRIVATE)
        val imageUri = sharedPref.getString("imageUri", null)
        val documentSoat = sharedPref.getString("documentSoat", null)
        val documentCDA = sharedPref.getString("documentCDA", null)
        val vehicleType = sharedPref.getString("vehicleType", null)
        val plate = sharedPref.getString("plate", null)
        val brand = sharedPref.getString("brand", null)
        val model = sharedPref.getString("model", null)

        // Check if any of the data is null or empty
        //return if (!imageUri.isNullOrEmpty() && !documentSoat.isNullOrEmpty() && !documentCDA.isNullOrEmpty() && !vehicleType.isNullOrEmpty() && !plate.isNullOrEmpty() && !brand.isNullOrEmpty() && !model.isNullOrEmpty()) {
            // Populate the RecyclerView with this data
            /*val vehicleData = AddDocumentActivity().apply {
                this.imageUri = Uri.parse(imageUri)
                this.documentSoat = Uri.parse(documentSoat)
                this.documentCDA = Uri.parse(documentCDA)
                this.vehicleType = vehicleType
                this.plate = plate
                this.brand = brand
                this.model = model
            }*/
        //}

        return if (imageUri != null && vehicleType != null && plate != null && brand != null && model != null) {
            listOf(Vehicle(imageUri, vehicleType, plate, brand, model))
        } else {
            emptyList()
        }
    }

    fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado
            }
        }
    }
}
/**********************************/