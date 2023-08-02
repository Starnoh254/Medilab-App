package com.starnoh.medilabsapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class SignUpActivity : AppCompatActivity() {

    private lateinit var selectedtext:TextView
    private lateinit var spinner:Spinner
    private lateinit var surname:TextInputEditText
    private lateinit var datePickerButton: Button
    private lateinit var others:TextInputEditText
    private lateinit var email:TextInputEditText
    private lateinit var phone:TextInputEditText
    private lateinit var password:TextInputEditText
    private lateinit var selectedDate: String
    var radioGroup:RadioGroup? = null
    lateinit var radioButton: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        surname = findViewById(R.id.surname)
        others = findViewById(R.id.others)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        password = findViewById(R.id.password)
        spinner = findViewById(R.id.spinner)
        selectedtext = findViewById(R.id.selectedtext)

        radioGroup = findViewById(R.id.radioGroupGender)
        val next = findViewById<MaterialButton>(R.id.nextbutton)

        val link = findViewById<MaterialTextView>(R.id.linktosignin)
        link.setOnClickListener {
            startActivity(Intent(applicationContext,SignInActivity::class.java))

        } // end onclick

        val password2 = findViewById<TextInputEditText>(R.id.confirm_password)

        val date_error = findViewById<MaterialTextView>(R.id.date_error)

        val data = listOf(" 1", " 2 ", " 3","  4")
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter



        next.setOnClickListener {
            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)

            if (password.text.toString() == ""){
                password.setError("This field is required")
            }
            else if(password.text.toString() != password2.text.toString()) {
                password2.setError("Password mis-match")

            }
            else if (selectedDate == ""){
                date_error.visibility = View.VISIBLE
            }
            else{
                post()
            }


        }







        datePickerButton = findViewById<MaterialButton>(R.id.datePickerButton)
        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }



    }// End OnCreate function

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Handle the selected date here
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()


    }

    fun post(){
        val helper = ApiHelper(applicationContext)
        val api = "${Constants.BASE_URL}/member_signup"
        val body = JSONObject()
        body.put("surname",surname.text)
        body.put("others",others.text)
        body.put("email",email.text)
        body.put("phone","+254"+ phone.text)
        body.put("password",password.text)
        body.put("location_id",spinner.selectedItem.toString())
        body.put("gender",radioButton.text)
        body.put("dob",selectedDate)
        helper.post(api,body, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                Toast.makeText(applicationContext, "You Successfully created an Account with us", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,SignInActivity::class.java))
            }

            override fun onFailure(result: String?) {

            }

        })
    }


}// End Class