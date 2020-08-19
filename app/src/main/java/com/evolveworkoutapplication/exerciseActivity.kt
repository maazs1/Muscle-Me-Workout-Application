package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercise.*

class exerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        exercise1.setOnClickListener {
            val intent = Intent(this, displayWorkoutActivity::class.java)
            startActivity(intent)
        }
    }


}