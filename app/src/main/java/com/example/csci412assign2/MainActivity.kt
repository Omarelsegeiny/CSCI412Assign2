package com.example.csci412assign2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val explicitButton = findViewById<Button>(R.id.button_explicit)
        val implicitButton = findViewById<Button>(R.id.button_implicit)
        val viewImageButton = findViewById<Button>(R.id.button_view_image)

        // Explicit Intent (Triggered on button click)
        explicitButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, "com.example.csci412assign2.MSE412") != PackageManager.PERMISSION_GRANTED) {
                // Request permission if not granted
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf("com.example.csci412assign2.MSE412"),
                    PERMISSION_REQUEST_CODE
                )

            } else {
                val explicitIntent = Intent(this, SecondActivity::class.java)
                startActivity(explicitIntent)
            }
        }

        // Implicit Intent
        implicitButton.setOnClickListener {
            val implicitIntent = Intent(Intent.ACTION_VIEW)
            startActivity(implicitIntent)
        }

        // View Image Activity (ThirdActivity)
        viewImageButton.setOnClickListener {
            val imageIntent = Intent(this, ThirdActivity::class.java)
            startActivity(imageIntent)
        }
    }

    // Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayToast("Permission granted! You can now access the Second Activity.")
            } else {
                displayToast("Permission denied. Cannot access Second Activity.")
            }
        }
    }

    private fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
