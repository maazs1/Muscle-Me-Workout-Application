package com.evolveworkoutapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class muscleInformation: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscleinformation)

        val bundle: Bundle? = intent.extras
        val username = bundle!!.getString("username")

    }
}