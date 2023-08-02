package com.starnoh.medilabsapp

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.modcom.medilabsapp.helpers.SQLiteCartHelper

class SingleLabTest : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_lab_test)
        // find the textview
        val textview_test_id = findViewById<MaterialTextView>(R.id.test_id)
        //Get test id value from the intent Extras
        val test_id = intent.extras?.getString("test_id")
        //put the test id value to textviewtestid
        textview_test_id.text = test_id

        val textview_test_name = findViewById<MaterialTextView>(R.id.test_name)
        val test_name = intent.extras?.getString("test_name")
        textview_test_name.text = test_name

        val textview_test_cost = findViewById<MaterialTextView>(R.id.test_cost)
        val test_cost = intent.extras?.getString("test_cost")
        textview_test_cost.text = "KES " + test_cost

        val textview_test_discount = findViewById<MaterialTextView>(R.id.test_discount)
        val test_discount = intent.extras?.getString("test_discount")
        if(test_discount?.toInt() == 0){
            println("Here1")
            textview_test_discount.visibility = View.GONE
        }
        else{
            textview_test_discount.text = test_discount + "% OFF"
            println("Here2")
        }


        val textview_test_description = findViewById<MaterialTextView>(R.id.test_description)
        val test_description = intent.extras?.getString("test_description")
        textview_test_description.text = test_description

        val textview_test_availability = findViewById<MaterialTextView>(R.id.availability)
        val availability = intent.extras?.getString("availability")
        textview_test_availability.text = availability

        val textview_test_more_info = findViewById<MaterialTextView>(R.id.more_info)
        val more_info = intent.extras?.getString("more_info")
        textview_test_more_info.text = more_info

        val addcart = findViewById<MaterialButton>(R.id.add_cart)
        addcart.setOnClickListener {
            val helper = SQLiteCartHelper(applicationContext)
            val lab_id = intent.extras?.getString("Lab_id")
            try{
                helper.insert(test_id!!, test_name!!, test_cost!!,
                    test_description!!, lab_id!!)
            }
            catch (e: Exception){
                Toast.makeText(applicationContext, "An Error Occurred.",
                    Toast.LENGTH_SHORT).show()
            }


            }

            //Done
        //end onclick

        // Get the URI of the video file
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.ad)
        val video1 = findViewById<VideoView>(R.id.video1)
        // Create a media controller for controlling playback
        val mediaController = MediaController(this)
        mediaController.setAnchorView(video1)

        // Set the media controller and video URI to the VideoView
        video1.setMediaController(mediaController)
        video1.setVideoURI(videoUri)

        // Start playing the video
        video1.start()
    }
}