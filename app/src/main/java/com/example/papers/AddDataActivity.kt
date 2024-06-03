package com.example.papers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class AddDataActivity : AppCompatActivity() {

    private lateinit var etPlate: EditText
    private lateinit var etBrand: EditText
    private lateinit var etModel: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        etPlate = findViewById(R.id.etPlate)
        etBrand = findViewById(R.id.etBrand)
        etModel = findViewById(R.id.etModel)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnCancel = findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            finish()
        }

        // Listener para los campos de texto
        etPlate.addTextChangedListener { checkFieldsNotEmpty() }
        etBrand.addTextChangedListener { checkFieldsNotEmpty() }
        etModel.addTextChangedListener { checkFieldsNotEmpty() }

        btnSubmit.setOnClickListener {
            val plate = etPlate.text.toString().trim()
            val brand = etBrand.text.toString().trim()
            val model = etModel.text.toString().trim()

            val resultIntent = Intent()
            resultIntent.putExtra("plate", plate)
            resultIntent.putExtra("brand", brand)
            resultIntent.putExtra("model", model)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun checkFieldsNotEmpty() {

        val plate = etPlate.text.toString().trim()
        val brand = etBrand.text.toString().trim()
        val model = etModel.text.toString().trim()

        // Habilitar o deshabilitar el botón según si los campos están vacíos o no
        btnSubmit.isEnabled = plate.isNotEmpty() && brand.isNotEmpty() && model.isNotEmpty()
    }
}