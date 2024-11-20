package com.example.csci412assign2

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.csci412assign2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val explicitButton = findViewById<Button>(R.id.button_explicit)
        val implicitButton = findViewById<Button>(R.id.button_implicit)
        val viewImageButton = findViewById<Button>(R.id.button_view_image)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startSecondActivity()
            } else {
                displayToast("Permission denied. Cannot access Second Activity.")
            }
        }

        if (ContextCompat.checkSelfPermission(this, "com.example.csci412assign2.MSE412") == PackageManager.PERMISSION_GRANTED) {
            startSecondActivity()
        } else {
            requestPermissionLauncher.launch("com.example.csci412assign2.MSE412")
        }

        explicitButton.setOnClickListener {
            requestPermissionLauncher.launch("com.example.csci412assign2.MSE412")
        }

        // Implicit Intent
        implicitButton.setOnClickListener {
            val implicitIntent =  Intent()
            startActivity(implicitIntent)
            
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setSupportActionBar(binding.toolbar)

            val navController = findNavController(R.id.nav_host_fragment_content_main)
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)

            binding.fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .setAnchorView(R.id.fab).show()
            }
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

    private fun startSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    private fun displayToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
