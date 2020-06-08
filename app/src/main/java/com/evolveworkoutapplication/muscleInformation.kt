package com.evolveworkoutapplication

import android.content.ComponentCallbacks2
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_muscleinformation.*
import kotlin.math.roundToInt


class muscleInformation: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muscleinformation)

        val bundle: Bundle? = intent.extras
        val username: String? = bundle?.getString("username")
        val age: String? = bundle?.getString("age")
        val gender: String? =bundle?.getString("gender")

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
            if (littleActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                if(mediumActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()
                    || reallyActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                    mediumActive.setBackgroundResource(R.drawable.pinkbutton_bg)
                    reallyActive.setBackgroundResource(R.drawable.pinkbutton_bg)
                }
                littleActive.setBackgroundResource(R.drawable.greenbutton_bg)
            }else{
                littleActive.setBackgroundResource(R.drawable.pinkbutton_bg)
            }
        }
        mediumActive.setOnClickListener {
            if (mediumActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                if(littleActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()
                    || reallyActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                    littleActive.setBackgroundResource(R.drawable.pinkbutton_bg)
                    reallyActive.setBackgroundResource(R.drawable.pinkbutton_bg)
                }
                mediumActive.setBackgroundResource(R.drawable.greenbutton_bg)
            }else{
                mediumActive.setBackgroundResource(R.drawable.pinkbutton_bg)
            }
        }

        reallyActive.setOnClickListener {
            if (reallyActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                if(littleActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()
                    || mediumActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                    littleActive.setBackgroundResource(R.drawable.pinkbutton_bg)
                    mediumActive.setBackgroundResource(R.drawable.pinkbutton_bg)
                }
                reallyActive.setBackgroundResource(R.drawable.greenbutton_bg)
            }else{
                reallyActive.setBackgroundResource(R.drawable.pinkbutton_bg)
            }
        }

        continueMuscleInfo.setOnClickListener {
            field1.onEditorAction(EditorInfo.IME_ACTION_DONE)
            field2.onEditorAction(EditorInfo.IME_ACTION_DONE)
            field3.onEditorAction(EditorInfo.IME_ACTION_DONE)

            if (field1.text.toString().trim().isNotEmpty() && field2.text.toString().trim().isNotEmpty() && field3.text.toString().trim().isNotEmpty()){
                if (littleActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState() ||
                    mediumActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState() ||
                    reallyActive.getBackground().getConstantState()!=getResources().getDrawable(R.drawable.pinkbutton_bg).getConstantState()){
                    calculateBodyFatPercentage(age!!.toInt(), gender!!)
                }
                else{
                    Toast.makeText(this, "Choose an Activity Level", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
            }
        }

//        val flipperImages = intArrayOf(
//            R.drawable.musclefrontimg,
//            R.drawable.musclesideimg
//        )
//
//        for (i in 0 until flipperImages.size){
//            setFlipper(flipperImages[i])
//        }

    }

    private fun calculateBodyFatPercentage(age:Int, gender: String) {
        if (littleActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.greenbutton_bg).getConstantState()){
            lessActivityCalc(age, gender)
        }
        if (mediumActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.greenbutton_bg).getConstantState()){
            fairlyActivityCalc(age, gender)
        }
        if (reallyActive.getBackground().getConstantState()==getResources().getDrawable(R.drawable.greenbutton_bg).getConstantState()){
            highlyActivityCalc(age, gender)
        }
    }

    private fun highlyActivityCalc(age:Int, gender:String){
        val abdominalCirc: Float
        val foreArmCirc :Float
        val thighCirc: Float
        val bodyFatPercentage : Int

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
    }

    private fun fairlyActivityCalc(age:Int, gender:String){
        val abdominalCirc: Float
        val foreArmCirc: Float
        val thighCirc: Float
        val bodyFatPercentage : Int

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
    }

    private fun lessActivityCalc(age:Int, gender: String){
        val foreArmCirc: Float
        val abdominalCirc :Float
        val thighCirc : Float
        val bodyFatPercentage : Int

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
    }
//    private fun setFlipper(imgs: Int){
//        val image = ImageView(applicationContext)
//        image.setBackgroundResource(imgs)
//        mood_roster.addView(image)
//    }
//    fun previousView(view: View?) {
//        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left)
//        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right)
//        viewFlipper.showPrevious()
//    }

//    fun nextView(view: View?) {
//        viewFlipper.setInAnimation(this, R.anim.slide_in_right)
//        viewFlipper.setOutAnimation(this, R.anim.slide_out_left)
//        viewFlipper.showNext()
//    }

}