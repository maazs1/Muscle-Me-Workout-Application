package com.evolveworkoutapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class homePage : AppCompatActivity() {

    private var username: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val actName:String = "Splash"

        val bundle: Bundle? = intent.extras
        if (actName.equals(bundle?.getString("Activity"))){
            username=getSharedPreferences("username", MODE_PRIVATE).getString("user", "")
        }else{
            username= bundle?.getString("username")

        }

        Toast.makeText(this, username, Toast.LENGTH_SHORT).show()

    }
}