package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bodyinformation.*
import kotlinx.android.synthetic.main.activity_signin.*
import java.math.BigInteger


class bodyInformation : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bodyinformation)

        val bundle: Bundle? = intent.extras
        val username = bundle!!.getString("username")
        username?.let { displayname(it) }

        continueBodyInfo.setOnClickListener {
            height_feet.onEditorAction(EditorInfo.IME_ACTION_DONE)
            height_inch.onEditorAction(EditorInfo.IME_ACTION_DONE)
            weight_lbs.onEditorAction(EditorInfo.IME_ACTION_DONE)
            age_text.onEditorAction(EditorInfo.IME_ACTION_DONE)
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
        val weight: Float = weight_lbs.text.toString().trim().toFloat()
        val BMI :Float = (703*weight)/(pow((height_ft*12+height_inc).toFloat(),(2)))
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

