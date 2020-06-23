package com.evolveworkoutapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val sp = getSharedPreferences("secure", MODE_PRIVATE)
            if(sp.getBoolean("logged", false)){
                val intent = Intent(this, loadingActivity::class.java)
                intent.putExtra("Activity", "Splash")
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this, Signin::class.java)
                startActivity(intent)
                finish()
            }


        }, 3000)
    }
}
