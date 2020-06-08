package com.evolveworkoutapplication

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

class Pop : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popimages)

        val DM = DisplayMetrics()
        windowManager.getDefaultDisplay().getMetrics(DM)

        val width :Int = DM.widthPixels
        val height : Int = DM.heightPixels

        window.setLayout(((width*.8).toInt()),((height*.6).toInt()))


    }
}


