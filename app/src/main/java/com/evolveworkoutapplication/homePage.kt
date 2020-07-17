package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_homepage.*
import java.text.DateFormat
import java.util.*

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

        homePageName.setText(username)

        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener() {
                calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
                    val date: String = (i1 + 1).toString() + "/" + i2 + "/" + i
                    Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
        })
        settingsButton.setOnClickListener {
            settingsClicked()
        }

    }

    private fun settingsClicked(){
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }

}

