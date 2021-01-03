package com.evolveworkoutapplication

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_muscleinformation.*
import kotlinx.android.synthetic.main.activity_signin.*
import kotlin.math.roundToInt


class muscleInformation: AppCompatActivity() {
    private var username:String?= null
    private var age:String?= null
    private var gender:String?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscleinformation)

        val bundle: Bundle? = intent.extras
        username = bundle?.getString("username")
        age = bundle?.getString("age")
        gender =bundle?.getString("gender")

        val field1Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("field1", " ")
        val field2Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("field2", " ")
        val field3Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("field3", " ")

        field1.setText(field1Org)
        field2.setText(field2Org)
        field3.setText(field3Org)


        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username!!)

        if (gender =="Male"){
            muscleInfo1.setText("Abdominal Circumference")
            muscleInfo2.setText("Forearm Circumference")
            if (age?.toInt()!! < 27){
                muscleInfo3.setText("Upper Arm Circumference")
            }
            else{
                muscleInfo3.setText("Buttock circumference")
            }
        } else if (gender == "Female"){
            muscleInfo1.setText("Abdominal Circumference")
            muscleInfo2.setText("Thigh Circumference")
            if (age?.toInt()!! < 27){
                muscleInfo3.setText("Forearm Circumference")
            }
            else{
                muscleInfo3.setText("Calf Circumference")
            }
        }

        popup.setOnClickListener {
            startActivity(Intent(this, Pop::class.java))
        }
        popupside.setOnClickListener{
            startActivity(Intent(this, PopSide::class.java))
        }
        littleActive.setOnClickListener {
            if (littleActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                if(mediumActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()
                    || reallyActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                    mediumActive.setBackgroundResource(R.drawable.bluebutton)
                    reallyActive.setBackgroundResource(R.drawable.bluebutton)
                }
                littleActive.setBackgroundResource(R.drawable.clickedbutton)
            }else{
                littleActive.setBackgroundResource(R.drawable.bluebutton)
            }
        }
        mediumActive.setOnClickListener {
            if (mediumActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                if(littleActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()
                    || reallyActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                    littleActive.setBackgroundResource(R.drawable.bluebutton)
                    reallyActive.setBackgroundResource(R.drawable.bluebutton)
                }
                mediumActive.setBackgroundResource(R.drawable.clickedbutton)
            }else{
                mediumActive.setBackgroundResource(R.drawable.bluebutton)
            }
        }

        reallyActive.setOnClickListener {
            if (reallyActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                if(littleActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()
                    || mediumActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                    littleActive.setBackgroundResource(R.drawable.bluebutton)
                    mediumActive.setBackgroundResource(R.drawable.bluebutton)
                }
                reallyActive.setBackgroundResource(R.drawable.clickedbutton)
            }else{
                reallyActive.setBackgroundResource(R.drawable.bluebutton)
            }
        }

        continueMuscleInfo.setOnClickListener {
            field1.onEditorAction(EditorInfo.IME_ACTION_DONE)
            field2.onEditorAction(EditorInfo.IME_ACTION_DONE)
            field3.onEditorAction(EditorInfo.IME_ACTION_DONE)

            getSharedPreferences(username, MODE_PRIVATE)
                .edit()
                .putString("field1", field1.text.toString().trim())
                .putString("field2", field2.text.toString().trim())
                .putString("field3", field3.text.toString().trim())
                .apply()

            if (field1.text.toString().trim().isNotEmpty() && field2.text.toString().trim().isNotEmpty() && field3.text.toString().trim().isNotEmpty()){
                if (littleActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState() ||
                    mediumActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState() ||
                    reallyActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.bluebutton).getConstantState()){
                    calculateBodyFatPercentage(age!!.toInt(), gender!!, username!!)
                }
                else{
                    Toast.makeText(this, "Choose an Activity Level", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
            }
        }

        backButtonMuscle.setOnClickListener{
            val intent = Intent(this, bodyInformation::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()
        }

    }

    private fun calculateBodyFatPercentage(age:Int, gender: String, username:String) {
        if (littleActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.clickedbutton).getConstantState()){
            lessActivityCalc(age, gender, username)
        }
        if (mediumActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.clickedbutton).getConstantState()){
            fairlyActivityCalc(age, gender, username)
        }
        if (reallyActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.clickedbutton).getConstantState()){
            highlyActivityCalc(age, gender, username)
        }
    }

    private fun highlyActivityCalc(age:Int, gender:String, username:String){
        val abdominalCirc: Float
        val foreArmCirc :Float
        val thighCirc: Float
        var bodyFatPercentage : Int =0

        if (age <27){
            if (gender == "Male"){
                val upperArmCirc: Float = field3.text.toString().trim().toFloat()
                abdominalCirc = field1.text.toString().trim().toFloat()
                foreArmCirc = field2.text.toString().trim().toFloat()

                bodyFatPercentage = ((upperArmCirc*3.7)+(abdominalCirc*1.31)-(foreArmCirc*5.43)-20.2).roundToInt()

            }else if (gender == "Female"){
                abdominalCirc= field1.text.toString().trim().toFloat()
                thighCirc = field2.text.toString().trim().toFloat()
                foreArmCirc = field3.text.toString().trim().toFloat()

                bodyFatPercentage = ((abdominalCirc*1.34)+ (thighCirc*2.08)-(foreArmCirc*4.31)-27.1).roundToInt()

            }

        }else if (age>26){
            if (gender == "Male"){
                val buttockCirc:Float = field3.text.toString().trim().toFloat()
                foreArmCirc = field2.text.toString().trim().toFloat()
                abdominalCirc = field1.text.toString().trim().toFloat()

                bodyFatPercentage= ((buttockCirc*1.05)+(abdominalCirc*0.9)-(foreArmCirc*3)-25).roundToInt()


            }else if (gender == "Female"){
                abdominalCirc = field1.text.toString().trim().toFloat()
                thighCirc = field2.text.toString().trim().toFloat()
                val calfCirc:Float = field3.text.toString().trim().toFloat()

                bodyFatPercentage = ((abdominalCirc*1.19)+(thighCirc*1.24)-(calfCirc*1.45)-25.9).roundToInt()

            }
        }
        addBodyFatToDatabase(bodyFatPercentage, username)

    }

    private fun fairlyActivityCalc(age:Int, gender:String, username:String){

        val abdominalCirc: Float
        val foreArmCirc: Float
        val thighCirc: Float
        var bodyFatPercentage : Int =0

        if (age < 27){
            if (gender == "Male"){
                val upperArmCirc : Float = field3.text.toString().trim().toFloat()
                abdominalCirc = field1.text.toString().trim().toFloat()
                foreArmCirc = field2.text.toString().trim().toFloat()

                bodyFatPercentage = ((upperArmCirc*3.7)+(abdominalCirc*1.31)-(foreArmCirc*5.43)-14.2).roundToInt()

            }else if (gender =="Female"){
                abdominalCirc = field1.text.toString().trim().toFloat()
                thighCirc = field2.text.toString().trim().toFloat()
                foreArmCirc = field3.text.toString().trim().toFloat()

                bodyFatPercentage = ((abdominalCirc*1.34)+(thighCirc*2.08)-(foreArmCirc*4.31)-22.6).roundToInt()

            }
        }else if (age >26){
            if (gender == "Male"){
                val buttockCirc: Float = field3.text.toString().trim().toFloat()
                abdominalCirc = field1.text.toString().trim().toFloat()
                foreArmCirc = field2.text.toString().trim().toFloat()

                bodyFatPercentage = ((buttockCirc*1.05)+ (abdominalCirc*0.9)-(foreArmCirc*3)-19).roundToInt()

            }else if (gender == "Female"){
                abdominalCirc =field1.text.toString().trim().toFloat()
                thighCirc = field2.text.toString().trim().toFloat()
                val calfCirc: Float = field3.text.toString().trim().toFloat()

                bodyFatPercentage = ((abdominalCirc*1.19)+(thighCirc*1.24)-(calfCirc*1.45)-21.4).roundToInt()

            }
        }
        addBodyFatToDatabase(bodyFatPercentage, username)

    }

    private fun lessActivityCalc(age:Int, gender: String, username:String){
        val foreArmCirc: Float
        val abdominalCirc :Float
        val thighCirc : Float
        var bodyFatPercentage : Int = 0

        if (age < 27){
            if (gender=="Male"){
                val upperArmCirc: Float= field3.text.toString().trim().toFloat()
                foreArmCirc= field2.text.toString().trim().toFloat()
                abdominalCirc = field1.text.toString().trim().toFloat()

                bodyFatPercentage = ((upperArmCirc*3.7)+(abdominalCirc*1.31)-(foreArmCirc*5.43)-10.2).roundToInt()


            }else if (gender =="Female"){
                abdominalCirc = field1.text.toString().trim().toFloat()
                thighCirc = field2.text.toString().trim().toFloat()
                foreArmCirc = field3.text.toString().trim().toFloat()

                bodyFatPercentage = ((abdominalCirc*1.34)+(thighCirc*2.08)-(foreArmCirc*4.31)-19.6).roundToInt()

            }
        }else if (age >26){
            if (gender =="Male"){
                val buttockCirc : Float = field3.text.toString().trim().toFloat()
                abdominalCirc = field1.text.toString().trim().toFloat()
                foreArmCirc= field2.text.toString().trim().toFloat()

                bodyFatPercentage = ((buttockCirc*1.05)+(abdominalCirc*0.9)-(foreArmCirc*3)-15).roundToInt()


            }else if (gender == "Female"){
                abdominalCirc = field1.text.toString().trim().toFloat()
                thighCirc = field2.text.toString().trim().toFloat()
                val calfCirc:Float = field3.text.toString().trim().toFloat()

                bodyFatPercentage = ((abdominalCirc*1.19)+(thighCirc*1.24)-(calfCirc*1.45)-18.4).roundToInt()

            }
        }
        addBodyFatToDatabase(bodyFatPercentage, username)

    }



    private fun addBodyFatToDatabase(bodyFatPercentage:Int, username:String){
        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username)
        accountDB.get()
            .addOnSuccessListener { document ->
                var weight: Long = (document.data?.get("Weight")) as Long
                var currentMuscleMass:Int = ((weight* (1-(bodyFatPercentage/100))).toInt())

                val bodyFatInformation = hashMapOf("Body_Fat_Percentage" to bodyFatPercentage,
                    "Current_Muscle_Mass" to currentMuscleMass)
                accountDB.update(bodyFatInformation as Map<String, Any>)

                val intent = Intent(this, physiqueActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("gender", gender)
                intent.putExtra("age", age.toString())
                startActivity(intent)
                finish()
            }
    }
}

