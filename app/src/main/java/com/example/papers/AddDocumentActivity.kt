package com.example.papers
import android.app.Activity
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
    private lateinit var btnAddDocument: Button
    private lateinit var btnGoBack: Button
    private lateinit var btnAddData: Button
    private lateinit var btnSave: Button
    private lateinit var imgPhotoStatus: ImageView
    private lateinit var imgDocumentStatus: ImageView

    private val PICK_IMAGE_REQUEST = 1
    private val PICK_DOCUMENT_REQUEST = 2

    private var imageUri: Uri? = null
    private var documentUri: Uri? = null
    private var vehicleType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_document)

        imgVehicle = findViewById(R.id.imgVehicle)
        radioGroup = findViewById(R.id.radioGroup)
        btnAddPhoto = findViewById(R.id.btnAddPhoto)
        btnAddDocument = findViewById(R.id.btnAddDocument)
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
            btnAddDocument.isEnabled = true
            btnAddPhoto.isEnabled = true
        }

        btnAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnAddDocument.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PICK_DOCUMENT_REQUEST)
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
            val intent = Intent()
            intent.putExtra("imageUri", imageUri.toString())
            intent.putExtra("documentUri", documentUri.toString())
            intent.putExtra("vehicleType", vehicleType)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
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
                        imgPhotoStatus.setImageResource(R.drawable.ic_error) // Your red X icon
                        imgPhotoStatus.visibility = ImageView.VISIBLE
                    }
                }
                PICK_DOCUMENT_REQUEST -> {
                    documentUri = uri
                    if (uri != null) {
                        imgPhotoStatus.setImageResource(R.drawable.ic_check)
                        showStatusImage(imgPhotoStatus)
                    } else {
                        imgDocumentStatus.setImageResource(R.drawable.ic_error) // Your red X icon
                        imgDocumentStatus.visibility = ImageView.VISIBLE
                    }
                }
                ADD_DATA_REQUEST -> {
                    // Handle additional data from AddDataActivity
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
        btnSave.isEnabled = imageUri != null && documentUri != null && vehicleType != null
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val PICK_DOCUMENT_REQUEST = 2
        private const val ADD_DATA_REQUEST = 3
    }
}
