package com.starnoh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import com.starnoh.medilabsapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class Complete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        // we are doing the booking phase

        // get member id
        val member_id = PrefsHelper.getPrefs(this,"member_id")

        // get date
        val date = PrefsHelper.getPrefs(this,"date")

        // get time
        val time  = PrefsHelper.getPrefs(this,"time")

        // get where taken
        val where_taken = PrefsHelper.getPrefs(this , "where_taken")

        // get booked for
        val booked_for = PrefsHelper.getPrefs(this, "booked_for")

        val latitude = PrefsHelper.getPrefs(this , "latitude")
        val longitude = PrefsHelper.getPrefs(this , "longitude")

        val test_id = 2 // static

        val lab_id = 8 // static
        val invoice_no = "INV123" // static * must be made dynamic

        val dependant_id = 2 // static

        // post to the api
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "/make_booking"
        val body = JSONObject()
        body.put("member_id", "4")
        body.put("where_taken","home")
        body.put("booked_for","self")
        body.put("test_id","2")
        body.put("lab_id","8")
        body.put("invoice_no","545454")
        body.put("dependant_id","2")
        body.put("latitude", latitude)
        body.put("longitude", longitude)
        body.put("appointment_date","2023-04-05")
        body.put("appointment_time","10:35:00")

        helper.post(api,body,object : ApiHelper.CallBack{
            // implementing the interfaces of the callback
            // our api returns a json  object
            override fun onSuccess(result: JSONArray?) {
            // will be done
            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(result: String?) {
            // will be done
            }
        })


        // Q: what will we do in this activity after getting all this data ?
    }
}