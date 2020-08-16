package com.evolveworkoutapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bodyinformation.*
import java.math.BigInteger


class bodyInformation : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bodyinformation)


        val bundle: Bundle? = intent.extras
        val username = bundle!!.getString("username")
        username?.let { displayname(it) }

        val heightFtOrg = getSharedPreferences(username, Context.MODE_PRIVATE)
            .getString("height_ft", " ")
        val heightIcOrg = getSharedPreferences(username, Context.MODE_PRIVATE)
            .getString("height_ic", " ")
        val weightLbOrg = getSharedPreferences(username, Context.MODE_PRIVATE)
            .getString("weight_lb", " ")
        val ageOrg = getSharedPreferences(username, Context.MODE_PRIVATE)
            .getString("ageT", " ")


        height_feet.setText(heightFtOrg)
        height_inch.setText(heightIcOrg)
        weight_lbs.setText(weightLbOrg)
        age_text.setText(ageOrg)

        continueBodyInfo.setOnClickListener {
            height_feet.onEditorAction(EditorInfo.IME_ACTION_DONE)
            height_inch.onEditorAction(EditorInfo.IME_ACTION_DONE)
            weight_lbs.onEditorAction(EditorInfo.IME_ACTION_DONE)
            age_text.onEditorAction(EditorInfo.IME_ACTION_DONE)


            getSharedPreferences(username, MODE_PRIVATE)
                .edit()
                .putString("height_ft", height_feet.text.toString().trim())
                .putString("height_ic", height_inch.text.toString().trim())
                .putString("weight_lb", weight_lbs.text.toString().trim())
                .putString("ageT", age_text.text.toString().trim())
                .apply()

            checkFields(username)

        }

        maleButton.setOnClickListener{
            if(maleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.maleimg).getConstantState()) {
                if (femaleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.femaleimg_onclick).getConstantState()){
                    femaleButton.setBackgroundResource(R.drawable.femaleimg)
                }
                maleButton.setBackgroundResource(R.drawable.maleimg_onclick)
            }
            else{
                maleButton.setBackgroundResource(R.drawable.maleimg)
            }
        }
        femaleButton.setOnClickListener {
            if(femaleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.femaleimg).getConstantState()) {
                if (maleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.maleimg_onclick).getConstantState()) {
                    maleButton.setBackgroundResource(R.drawable.maleimg)
                }
                femaleButton.setBackgroundResource(R.drawable.femaleimg_onclick)
            }
            else{
                femaleButton.setBackgroundResource(R.drawable.femaleimg)
            }
        }
    }

    private fun continueBodyInfo(username: String?){
        val height_ft: Float= height_feet.text.toString().trim().toFloat()
        val height_inc: Float= height_inch.text.toString().trim().toFloat()
        val Age: Int = age_text.text.toString().trim().toInt()
        val weight: Int = weight_lbs.text.toString().trim().toInt()
        val BMI : Int = (703*weight)/(pow((height_ft*12+height_inc),(2))).toInt()
        val Gender :String
        if (maleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.maleimg_onclick).getConstantState()){
            Gender = "Male"
        }
        else{
            Gender = "Female"
        }
        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username!!)
        val profileInformation = hashMapOf("Height_Feet" to height_ft, "Height_Inch" to height_inc, "Age" to Age, "Weight" to weight, "BMI" to BMI, "Gender" to Gender)
        accountDB.update(profileInformation as Map<String, Any>)
        val intent = Intent(this, muscleInformation::class.java)
        intent.putExtra("username", username)
        intent.putExtra("age", Age.toString())
        intent.putExtra("gender", Gender)
        startActivity(intent)
        finish()
    }

    fun pow(n: Float, exp: Int): Float{
        return BigInteger.valueOf(n.toLong()).pow(exp).toFloat()
    }

    private fun checkFields(username: String?){
        if (height_feet.text.toString().trim().isNotEmpty() && height_feet.text.toString().trim().isNotEmpty() && weight_lbs.text.toString().trim().isNotEmpty() && age_text.text.toString().trim().isNotEmpty()){
            if(maleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.maleimg_onclick).getConstantState() || femaleButton.getBackground().getConstantState()==getResources().getDrawable(R.drawable.femaleimg_onclick).getConstantState() ){
                continueBodyInfo(username)
            }
            else{Toast.makeText(this, "Select a Gender", Toast.LENGTH_SHORT).show()}
        }else{
            Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayname(username: String){
        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username)
        accountDB.get()
            .addOnSuccessListener { document ->
                nameText.setText(document.id)
            }
    }

}





