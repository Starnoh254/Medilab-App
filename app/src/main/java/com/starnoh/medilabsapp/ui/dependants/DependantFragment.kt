package com.starnoh.medilabsapp.ui.dependants

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import com.starnoh.medilabsapp.R
import com.starnoh.medilabsapp.ViewDependant
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import com.starnoh.medilabsapp.helpers.PrefsHelper
import com.starnoh.medilabsapp.models.Dependant
import com.starnoh.medilabsapp.models.LabTests
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class DependantFragment : Fragment() {
    lateinit var selectedDate:String
    private lateinit var datePickerButton: Button
    private lateinit var surname: TextInputEditText
    private lateinit var others: TextInputEditText
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private lateinit var viewDependant : MaterialButton



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_dependant, container, false)
        datePickerButton = root.findViewById<MaterialButton>(R.id.datePickerButton)
        datePickerButton.setOnClickListener {
            showDatePickerDialog()
        }

        val add_dependant = root.findViewById<MaterialButton>(R.id.add_dependant)

        surname = root.findViewById(R.id.surname)
        others = root.findViewById(R.id.others)
        radioGroup = root.findViewById(R.id.radioGroupGender)
        viewDependant = root.findViewById(R.id.view_dependant)

        add_dependant.setOnClickListener {
            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = root.findViewById(intSelectButton)
            if(surname.text!!.isEmpty())
                surname.setError("This field is empty")
            else if (others.text!!.isEmpty()){
                others.setError("This field is required")
            }else{

            }

        }
        viewDependant.setOnClickListener {
            startActivity(Intent(requireContext(), ViewDependant::class.java))
        }
        return root
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Handle the selected date here
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                Toast.makeText(requireContext(), "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()


    }


}