package com.example.papers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler

class AddDocumentActivity : AppCompatActivity() {

    private lateinit var imgVehicle: ImageView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnAddPhoto: Button
    private lateinit var btnAddSoat: Button
    private lateinit var btnAddCDA: Button
    private lateinit var btnGoBack: Button
    private lateinit var btnAddData: Button
    private lateinit var btnSave: Button
    private lateinit var imgPhotoStatus: ImageView
    private lateinit var imgDocumentStatus: ImageView

    var imageUri: Uri? = null
    var documentSoat: Uri? = null
    var documentCDA: Uri? = null
    var vehicleType: String? = null

    var plate: String? = null
    var brand: String? = null
    var model: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_document)

        imgVehicle = findViewById(R.id.imgVehicle)
        radioGroup = findViewById(R.id.radioGroup)
        btnAddPhoto = findViewById(R.id.btnAddPhoto)
        btnAddSoat = findViewById(R.id.btnAddSoat)
        btnAddCDA = findViewById(R.id.btnAddCDA)
        btnGoBack = findViewById(R.id.btnGoBack)
        btnAddData = findViewById(R.id.btnAddData)
        btnSave = findViewById(R.id.btnSave)
        imgPhotoStatus = findViewById(R.id.imgPhotoStatus)
        imgDocumentStatus = findViewById(R.id.imgDocumentStatus)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            vehicleType = when (checkedId) {
                R.id.radioMotorcycle -> "Motocicleta"
                R.id.radioCar -> "Automóvil"
                else -> null
            }
            checkEnableSaveButton()
            btnAddSoat.isEnabled = true
            btnAddCDA.isEnabled = true
            btnAddPhoto.isEnabled = true
            btnAddData.isEnabled = true
        }

        btnAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnAddSoat.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PICK_DOCUMENT_REQUEST)
        }

        btnAddCDA.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PICK_DOCUMENT_REQUEST2)
        }

        btnGoBack.setOnClickListener {
            finish()
        }

        btnAddData.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivityForResult(intent, ADD_DATA_REQUEST)
        }

        btnSave.setOnClickListener {
            // Save the data and navigate to the main view to display the card
            saveDataToSharedPreferences()
            //val intent = Intent(this, MainActivity::class.java)
            val intent = Intent().apply {
                putExtra("imageUri", imageUri.toString())
                putExtra("documentSoat", documentSoat.toString())
                putExtra("documentCDA", documentCDA.toString())
                putExtra("vehicleType", vehicleType)
                putExtra("plate", plate)
                putExtra("brand", brand)
                putExtra("model", model)
            }
            setResult(Activity.RESULT_OK, intent)
            //startActivity(intent)
            finish()
        }
    }

    private fun saveDataToSharedPreferences() {
        val sharedPref = getSharedPreferences("com.example.papers.PREFERENCES", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("imageUri", imageUri?.toString() ?: "")
        editor.putString("documentSoatUri", documentSoat?.toString() ?: "")
        editor.putString("documentCDAUri", documentCDA?.toString() ?: "")
        editor.putString("vehicleType", vehicleType ?: "")
        editor.putString("plate", plate ?: "")
        editor.putString("model", brand ?: "")
        editor.putString("brand", model ?: "")
/*
        val intent = Intent()
        intent.putExtra("imageUri", imageUri.toString())
        intent.putExtra("documentUri", documentSoat.toString())
        intent.putExtra("documentUri", documentCDA.toString())
        intent.putExtra("vehicleType", vehicleType)
        intent.putExtra("plate", plate)
        intent.putExtra("brand", brand)
        intent.putExtra("model", model)
        setResult(Activity.RESULT_OK, intent)
*/
        editor.apply()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri: Uri? = data.data

            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    imageUri = uri
                    if (uri != null) {
                        imgVehicle.setImageURI(uri)
                        imgVehicle.adjustViewBounds = true
                        imgVehicle.scaleType = ImageView.ScaleType.CENTER_CROP
                        imgPhotoStatus.setImageResource(R.drawable.ic_check)
                        showStatusImage(imgPhotoStatus)
                    } else {
                        imgPhotoStatus.setImageResource(R.drawable.ic_error)
                        imgPhotoStatus.visibility = ImageView.VISIBLE
                    }
                }
                PICK_DOCUMENT_REQUEST -> {
                    documentSoat = uri
                    if (uri != null) {
                        imgDocumentStatus.setImageResource(R.drawable.ic_check)
                        showStatusImage(imgDocumentStatus)
                    } else {
                        imgDocumentStatus.setImageResource(R.drawable.ic_error) // Your red X icon
                        imgDocumentStatus.visibility = ImageView.VISIBLE
                    }
                }
                PICK_DOCUMENT_REQUEST2 -> {
                    documentCDA = uri
                    if (uri != null) {
                        imgDocumentStatus.setImageResource(R.drawable.ic_check)
                        showStatusImage(imgDocumentStatus)
                    } else {
                        imgDocumentStatus.setImageResource(R.drawable.ic_error) // Your red X icon
                        imgDocumentStatus.visibility = ImageView.VISIBLE
                    }
                }
                ADD_DATA_REQUEST -> {
                    plate = data.getStringExtra("plate")
                    brand = data.getStringExtra("brand")
                    model = data.getStringExtra("model")
                }
            }
            checkEnableSaveButton()
        }
    }

    private fun showStatusImage(imageView: ImageView) {
        imageView.visibility = ImageView.VISIBLE
        Handler().postDelayed({
            imageView.visibility = ImageView.GONE
        }, 500)
    }

    private fun checkEnableSaveButton() {
        btnSave.isEnabled = imageUri != null && documentSoat != null && documentCDA != null && vehicleType != null && plate != null && brand != null && model != null
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val PICK_DOCUMENT_REQUEST = 2
        private const val PICK_DOCUMENT_REQUEST2 = 4
        private const val ADD_DATA_REQUEST = 3
    }

}
