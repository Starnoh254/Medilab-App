package com.starnoh.medilabsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.loopj.android.http.AsyncHttpClient.log

class CheckOutStep2GPS : AppCompatActivity() {
    private lateinit var  editlatitude: TextInputEditText
    private lateinit var  editLongitude: TextInputEditText
    private lateinit var getLocation : MaterialButton
    private lateinit var progress : ProgressBar
    private lateinit var skips: MaterialTextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun getAddress(latlng: LatLng) : String{
        val geoCoder = Geocoder(this)
        val list = geoCoder.getFromLocation(latlng.latitude,latlng.longitude,1)
        return  if(list!!.isEmpty()){""} else list!![0].getAddressLine(0)
    }

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

                    val place = getAddress(LatLng(it.latitude,it.longitude))

                    var skips = findViewById<MaterialTextView>(R.id.skips)
                    skips.text = "Current Location is : $place"

                    requestNewLocation()
                }?: run {
                    Toast.makeText(applicationContext, "Searching Location", Toast.LENGTH_SHORT).show()
                    requestNewLocation()
                }
        }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, "Error $e", Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
                requestNewLocation()

            }
    }// end getLocation()

    lateinit var mLocationCallback: LocationCallback

    @SuppressLint("MissingPermission")
    fun requestNewLocation(){
        progress.visibility = View.VISIBLE
        log.d("hhhhhh", "Requesting New Location")
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 10000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations){
                    if (location!= null){
                        editlatitude.setText(location.latitude.toString())
                        editLongitude.setText(location.longitude.toString())
                        progress.visibility = View.GONE
                    }
                    else{
                        Toast.makeText(applicationContext, "Please check your location settings", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest,
        mLocationCallback, Looper.getMainLooper())

    }





}// end class

