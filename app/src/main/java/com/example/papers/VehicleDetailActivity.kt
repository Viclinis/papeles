package com.example.papers
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class VehicleDetailActivity : AppCompatActivity() {

    private lateinit var imgVehicle: ImageView
    private lateinit var txtVehicleType: TextView
    private lateinit var txtPlate: TextView
    private lateinit var btnGoBack: Button

    //@RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_detail)

        imgVehicle = findViewById(R.id.imgVehicleDetail)
        txtVehicleType = findViewById(R.id.txtVehicleTypeDetail)
        txtPlate = findViewById(R.id.txtVehiclePlateDetail)
        btnGoBack = findViewById(R.id.btnGoBackDetail)

        val imageUri = intent.getStringExtra("imageUri")
        val vehicleType = intent.getStringExtra("vehicleType")
        val plate = intent.getStringExtra("plate")

        if (imageUri != null) {
            loadImage(Uri.parse(imageUri))
        }
        txtVehicleType.text = vehicleType
        txtPlate.text = plate

        btnGoBack.setOnClickListener {
            finish()
        }
    }

    private fun loadImage(uri: Uri) {
        try {
            val bitmap: Bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
            imgVehicle.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Log.e("VehicleDetailActivity", "Error loading image: ${e.message}")
        }
    }
}