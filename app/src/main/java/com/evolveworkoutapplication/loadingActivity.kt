package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_homepage.*

class loadingActivity : AppCompatActivity(){


    private var username: String? = null
    private var flag: String? = null
    private var gender: String? = null
    private var returnExp:Int? = null
    private var goalPerFat: Int? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)


        val actName: String = "Splash"

        val bundle: Bundle? = intent.extras
        if (actName.equals(bundle?.getString("Activity"))) {
            username = getSharedPreferences("username", MODE_PRIVATE).getString("user", "")
        } else {
            username = bundle?.getString("username")
        }

        Handler().postDelayed(this::getImgFlag, 3000)
    }

    private fun getImgFlag(){
        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username.toString())
        accountDB.get()
            .addOnSuccessListener { document ->
                flag = document.data?.get("Flag")?.toString()
                gender = document.data?.get("Gender")?.toString()
                var ImageResource: Int? = displayImg(flag, gender)
                var goalW=(document.data?.get("Goal_Weight")?.toString())
                var currentW=( document.data?.get("Weight")?.toString())
                var currentBodyFP=(document.data?.get("Body_Fat_Percentage")?.toString()) + "%"
                var goalMuscleM =(document.data?.get("Goal_Muscle_Mass")?.toString())
                var currentMuscleM = (document.data?.get("Current_Muscle_Mass")?.toString())

                val intent = Intent(this, homePage::class.java)
                intent.putExtra("username", username)
                intent.putExtra("ImageResource", ImageResource)
                intent.putExtra("goalW", goalW)
                intent.putExtra("currentW", currentW)
                intent.putExtra("currentBodyFP", currentBodyFP)
                intent.putExtra("goalMuscleM", goalMuscleM)
                intent.putExtra("currentMuscleM", currentMuscleM)
                intent.putExtra("goalPF", goalPerFat)

                startActivity(intent)
                finish()

            }
    }

    private fun displayImg(flag: String?, gender: String?): Int? {

        if (gender =="Male"){
            when (flag?.toInt()){
                1 -> {returnExp= R.drawable.male_physique_first
                        goalPerFat=18}
                2 -> {returnExp= R.drawable.male_physique_second
                        goalPerFat=15}
                3 -> {returnExp= R.drawable.male_physique_third
                        goalPerFat=12}
                4 -> {returnExp= R.drawable.male_physique_fourth
                        goalPerFat=10}
            }
        }
        else if (gender == "Female"){
            when (flag?.toInt()){
                1 -> {returnExp= R.drawable.female_physique_first
                        goalPerFat=21}
                2 -> {returnExp= R.drawable.female_physique_second
                        goalPerFat=18}
                3 -> {returnExp= R.drawable.female_physique_third
                        goalPerFat=14}
                4 -> {returnExp= R.drawable.female_physique_fourth
                        goalPerFat=12}
            }
        }
        return returnExp
    }


}