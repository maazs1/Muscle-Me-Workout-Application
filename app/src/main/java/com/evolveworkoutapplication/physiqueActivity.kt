package com.evolveworkoutapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class physiqueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_physique)

        val bundle: Bundle? = intent.extras
        val username: String? = bundle?.getString("username")
        val gender: String? =bundle?.getString("gender")
    }
}