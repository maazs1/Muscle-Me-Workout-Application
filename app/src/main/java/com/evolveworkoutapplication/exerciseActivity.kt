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
            exerciseActivity()
        }
        exercise2.setOnClickListener {
            exerciseActivity()
        }
        exercise3.setOnClickListener {
            exerciseActivity()
        }
        exercise4.setOnClickListener {
            exerciseActivity()
        }
        exercise5.setOnClickListener {
            exerciseActivity()
        }
        exercise6.setOnClickListener {
            exerciseActivity()
        }
        exercise7.setOnClickListener {
            exerciseActivity()
        }
        exercise8.setOnClickListener {
            exerciseActivity()
        }
        exercise9.setOnClickListener {
            exerciseActivity()
        }
        exercise10.setOnClickListener {
            exerciseActivity()
        }
        exercise11.setOnClickListener {
            exerciseActivity()
        }
        exercise12.setOnClickListener {
            exerciseActivity()
        }

    }
    private fun exerciseActivity(){
        val intent = Intent(this, displayWorkoutActivity::class.java)
        startActivity(intent)
    }

}