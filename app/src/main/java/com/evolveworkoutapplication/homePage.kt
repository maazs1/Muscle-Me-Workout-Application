package com.evolveworkoutapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_homepage.*

class homePage : AppCompatActivity() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val bundle: Bundle? = intent.extras

        username = bundle?.getString("username")
        goal_weight.append(" "+(bundle?.getString("goalW")))
        current_weight.append(" "+(bundle?.getString("currentW")))
        current_fat.append(" "+(bundle?.getString("currentBodyFP")))
        goal_muscle.append(" "+(bundle?.getString("goalMuscleM")))
        current_muscle.append(" "+(bundle?.getString("currentMuscleM")))
        goal_fat.append(" "+(bundle?.getInt("goalPF")))
        homePageImg.setImageResource((bundle?.getInt("ImageResource"))!!)

    }

}