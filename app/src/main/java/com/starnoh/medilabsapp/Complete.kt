package com.starnoh.medilabsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.modcom.medilabsapp.helpers.SQLiteCartHelper
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import com.starnoh.medilabsapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Complete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        // lets get to SQLite , fetch the tests picked by user
        val sqllitehelper = SQLiteCartHelper(applicationContext)
        val items = sqllitehelper.getAllItems()
        val invoice_no = generateInvoiceNumber() // Auto Generate
        items.forEach() {

        // we are doing the booking phase

        // get member id
        val member_id = PrefsHelper.getPrefs(this, "member_id")

        // get date
        val date = PrefsHelper.getPrefs(this, "date")

        // get time
        val time = PrefsHelper.getPrefs(this, "time")

        // get where taken
        val where_taken = PrefsHelper.getPrefs(this, "where_taken")

        // get booked for
        val booked_for = PrefsHelper.getPrefs(this, "booked_for")

        val latitude = PrefsHelper.getPrefs(this, "latitude")
        val longitude = PrefsHelper.getPrefs(this, "longitude")

        val test_id = it.test_id  //  capture test id at a given loop

        val lab_id = it.lab_id // capture lab id at a given loop


        val dependant_id = PrefsHelper.getPrefs(this, "dependant_id")

        // post to the api
        val helper = ApiHelper(this)
        val api = Constants.BASE_URL + "/make_booking"
        val body = JSONObject()
        body.put("member_id", member_id)
        body.put("where_taken", where_taken)
        body.put("booked_for", booked_for)
        body.put("test_id", test_id)
        body.put("lab_id", lab_id)
        body.put("invoice_no", invoice_no)
        body.put("dependant_id", dependant_id)
        body.put("latitude", latitude)
        body.put("longitude", longitude)
        body.put("appointment_date", date)
        body.put("appointment_time", time)

        helper.post(api, body, object : ApiHelper.CallBack {
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
    }


        // Q: what will we do in this activity after getting all this data ?
    }// end on create

    fun generateInvoiceNumber () : String{
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val currentTime = Date()
        val timestamp = dateFormat.format(currentTime)
        // You can use a random number or  sequential number to add uniqueness
        // For example , using a random number:
        val random = Random()
        val randomNumber = random.nextInt(1000) // Change the upper bound to 1000

        // Combine the timestamp and random number to create the invoice number

        return "INV-$timestamp-$randomNumber"
    }
}