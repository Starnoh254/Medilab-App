package com.starnoh.medilabsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.starnoh.medilabsapp.databinding.FragmentHomeBinding
import com.starnoh.medilabsapp.helpers.PrefsHelper
import org.json.JSONObject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val userObject = PrefsHelper.getPrefs(requireContext(), "userObject")
        val user = JSONObject(userObject)
        val surname = _binding!!.surname
        surname.text = "Surname :" + user.getString("surname")

        val others = _binding!!.others
        others.text = "Others: "+ user.getString("others")

        val gender = _binding!!.gender
        gender.text = "gender: "+ user.getString("gender")

        val dob = _binding!!.dob
        dob.text = "Date of Birth: " + user.getString("dob")

        val reg_date = _binding!!.regDate
        reg_date.text = "Registration Date: "+ user.getString("reg_date")

        val email = _binding!!.email
        email.text = "Email : " + user.getString("email")

        val phone = _binding!!.phone
        phone.text = "Phone :  " + user.getString("phone")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}