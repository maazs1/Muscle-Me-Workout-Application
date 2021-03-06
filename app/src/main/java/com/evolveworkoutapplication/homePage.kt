package com.evolveworkoutapplication


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_homepage.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Math.abs
import java.util.Calendar.DAY_OF_MONTH


class homePage : AppCompatActivity() {

    private var username: String? = null
    private var currentBodyFat:Int? = null
    private var goalBodyFat:Int? = null

    private var output : String? = null

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

        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username!!)

        val sp = getSharedPreferences(username, MODE_PRIVATE)
        if (sp.getBoolean("Ran", false)){
            Log.d("mamamade", "Loop will not run")
        }
        else{
            Log.d("mamamade", "Loop will run")
            runLoop()
        }
        getSharedPreferences(username, MODE_PRIVATE)
            .edit()
            .putBoolean("Ran",true)
            .apply()

        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener() {
                calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
                    val date: String = (i1 + 1).toString() + "/" + i2 + "/" + i
                    Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, exerciseActivity::class.java)
            startActivity(intent)
        })

        settingsButton.setOnClickListener {
            settingsClicked()
        }
    }

    private fun runLoop(){
        val differenceFat = abs(currentBodyFat?.minus(goalBodyFat!!)!!)
        Log.d("mamamade", differenceFat.toString())
        if (differenceFat!! <=2){
            Log.d("mamamade", "Step2")
            val flag: Int = 2
            outputWorkout(17)
        }

        else{
            Log.d("mamamade", "Step17")
            val flag: Int = 17
            outputWorkout(2)
        }

    }

    private fun settingsClicked(){
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
    private fun outputWorkout(step:Int){
        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username.toString())
        accountDB.get()
            .addOnSuccessListener { document ->
                val goalBMI = document.data?.get("GoalBMI")?.toString()
                val currentBMI = document.data?.get("BMI")?.toString()
                val diffBMI: Int = (currentBMI?.toInt())!! - (goalBMI?.toInt())!!

                if(step==2){
                    if (diffBMI>0 && diffBMI<10){
                        output="Cu"
                    }

                }else if (step==17){
                    if (diffBMI>0 && diffBMI<2){
                        output="Cu"
                    }
                }
                if (diffBMI <0){
                    output = "B"
                }
                else if (diffBMI>0){
                    output="Ca"
                }
                Toast.makeText(this, output, Toast.LENGTH_SHORT).show()
            }

    }
}

