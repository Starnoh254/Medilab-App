package com.starnoh.medilabsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class CheckOutStep2GPS : AppCompatActivity() {
    private lateinit var  editlatitude: TextInputEditText
    private lateinit var  editLongitude: TextInputEditText
    private lateinit var getLocation : MaterialButton
    private lateinit var progress : ProgressBar
    private lateinit var skips: MaterialTextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step2_gps)

        editlatitude = findViewById(R.id.editlatitude)
        editLongitude = findViewById(R.id.editlongitude)
        progress = findViewById(R.id.progress)
        getLocation = findViewById(R.id.getLocation)
        skips = findViewById(R.id.skips)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        progress.visibility = View.GONE
        getLocation.setOnClickListener {
            progress.visibility = View.VISIBLE
            requestLocation()
        }

    }
    fun requestLocation(){
        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                123
            )
        }
        else{
            getLocation()
        }
        } // end requestLocation()

    @SuppressLint("MissingPermission")
    fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
            location ->
                location?.let {
                    editlatitude.setText(it.latitude.toString())
                    editLongitude.setText(it.longitude.toString())
                    progress.visibility  =  View.GONE
                }?: run {
                    Toast.makeText(applicationContext, "Location Not Found", Toast.LENGTH_SHORT).show()
                }
        }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, "Error $e", Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE

            }
    }// end getLocation()


}// end class

