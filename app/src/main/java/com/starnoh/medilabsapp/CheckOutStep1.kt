package com.starnoh.medilabsapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CheckOutStep1 : AppCompatActivity() {
    private lateinit var datePickerButton: Button
    private lateinit var timePickerButton: Button
    private lateinit var editText: EditText
    private lateinit var editTime: EditText
    var radioGroup: RadioGroup? = null
    var radioGroup2: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private lateinit var selectedDate: String
    private lateinit var nextbutton : MaterialButton
    private lateinit var selectedTime: String

    
    private fun showTimePicker(){
        val calender = Calendar.getInstance()
        val timePickerDialog =  TimePickerDialog(
            this,
            timeSetListener,
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            false)
        timePickerDialog.show()
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
         selectedTime = sdf.format(calendar.time)
        editTime.setText(selectedTime) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out_step1)

        nextbutton = findViewById(R.id.nextbutton)
        radioGroup = findViewById(R.id.radioGroupPerson)

        datePickerButton = findViewById(R.id.datePickerButton)
        editTime = findViewById(R.id.editTime)
        editText = findViewById(R.id.editText)
        radioGroup = findViewById(R.id.radioGroupPerson)
        radioGroup2 = findViewById(R.id.radioGroupLocation)


        datePickerButton = findViewById<MaterialButton>(R.id.datePickerButton)
        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }
        timePickerButton = findViewById(R.id.timePickerButton)
        timePickerButton.setOnClickListener {
            showTimePicker()
        }

        nextbutton.setOnClickListener {
            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)

            val intSelectButton2 : Int = radioGroup2!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton2)
            startActivity(Intent(applicationContext, CheckOutStep2GPS::class.java))
            /*if (selectedDate.isEmpty() || selectedTime.isEmpty() || radioButton.text.isEmpty()){
                Toast.makeText(applicationContext, "Empty Fields ", Toast.LENGTH_SHORT).show()
            }
            else{
                startActivity(Intent(applicationContext, CheckOutStep2GPS::class.java))

            }*/


        }

    }

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
                editText.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()


    }


}