package com.starnoh.medilabsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class Screen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen2)

        // find view by id
        val skip1 = findViewById<MaterialTextView>(R.id.skip2)
        skip1.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))

        }//End

        val fab = findViewById<FloatingActionButton>(R.id.fab2)
        fab.setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity ::class.java))
        }

    }
}