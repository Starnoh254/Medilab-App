package com.starnoh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.starnoh.medilabsapp.constants.Constants
import com.starnoh.medilabsapp.helpers.ApiHelper
import com.starnoh.medilabsapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    private lateinit var surname : TextInputEditText
    private lateinit var password : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        surname = findViewById(R.id.surname)
        password = findViewById(R.id.password)
        val password2 = findViewById<TextInputEditText>(R.id.confirm_password)
        val signIn = findViewById<MaterialButton>(R.id.nextbutton)
        signIn.setOnClickListener {
            if (password.text.toString() == ""){
                password.setError("This field is required")
            }
            else if(password.text.toString() != password2.text.toString()) {
                password2.setError("Password mis-match")

            }else{
                post()
            }


        }

        val link = findViewById<MaterialTextView>(R.id.linktosignup).setOnClickListener{
            startActivity(Intent(applicationContext, SignUpActivity::class.java))

        }
    }
    private fun post(){
        val api = "${Constants.BASE_URL}/member_signin"
        val body = JSONObject()
        val helper = ApiHelper(applicationContext)
        body.put("surname" , surname.text)
        body.put("password", password.text)
        helper.post(api,body, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                if (result!!.has("refresh_token")){
                    val access_token = result.getString("access_token")
                    val refresh_token = result.getString("refresh_token")
                    val message = result.getString("message")// {} Object user details
                    Toast.makeText(applicationContext, "You have successfully logged in ", Toast.LENGTH_SHORT).show()

                    PrefsHelper.savePrefs(applicationContext,"refresh_token",refresh_token)
                    PrefsHelper.savePrefs(applicationContext,"access_token", access_token)

                    //convert message to an object
                    val member = JSONObject(message)
                    val member_id = member.getString("member_id")
                    val email = member.getString("email")
                    val surname = member.getString("surname")

                    PrefsHelper.savePrefs(applicationContext,"member_id", member_id)
                    PrefsHelper.savePrefs(applicationContext,"email", email)
                    PrefsHelper.savePrefs(applicationContext, "surname", surname)

                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    finishAffinity()

                    PrefsHelper.savePrefs(applicationContext,"userObject", message)
                }else{
                    Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(result: String?) {

            }

        })
    }
}