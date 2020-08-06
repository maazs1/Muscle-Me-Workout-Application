package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private var currentBodyFat:Int? = null
    private var goalBodyFat:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val bundle: Bundle? = intent.extras

        currentBodyFat = bundle?.getString("currentBodyFP")?.toInt()
        goalBodyFat = bundle?.getInt("goalPF")

        username = bundle?.getString("username")
        goal_weight.append(" "+(bundle?.getString("goalW")))
        current_weight.append(" "+(bundle?.getString("currentW")))
        current_fat.append(" $currentBodyFat")
        goal_muscle.append(" "+(bundle?.getString("goalMuscleM")))
        current_muscle.append(" "+(bundle?.getString("currentMuscleM")))
        goal_fat.append(" $goalBodyFat")
        homePageImg.setImageResource((bundle?.getInt("ImageResource"))!!)

        homePageName.setText(username)


        val sp = getSharedPreferences("FirstRun", MODE_PRIVATE)
        if (sp.getBoolean("Ran", false)){
            Log.d("Debug", "Always show")
        }
        else{
            runLoop()
        }
        getSharedPreferences("FirstRun", MODE_PRIVATE)
            .edit()
            .putBoolean("Ran",true)
            .apply()

        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener() {
                calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
                    val date: String = (i1 + 1).toString() + "/" + i2 + "/" + i
                    Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
        })
        settingsButton.setOnClickListener {
            settingsClicked()
        }
    }

    private fun runLoop(){
        val differenceFat = currentBodyFat?.minus(goalBodyFat!!)
        Toast.makeText(this, differenceFat!!.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun settingsClicked(){
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }

}

