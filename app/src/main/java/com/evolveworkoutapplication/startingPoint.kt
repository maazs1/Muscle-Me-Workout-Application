package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_startpoint.*

class startingPoint : AppCompatActivity() {

    private var username: String?=null
    private var gender: String?=null
    private var age: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startpoint)

        val bundle: Bundle? = intent.extras
        username= bundle?.getString("username")
        age = bundle?.getString("age")
        gender= bundle?.getString("gender")

        val text1Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text1", " ")
        val text2Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text2", " ")
        val text3Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text3", " ")
        val text4Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text4", " ")
        val text5Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text5", " ")
        val text6Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text6", " ")
        val text7Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text7", " ")
        val text8Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text8", " ")
        val text9Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text9", " ")
        val text10Org = getSharedPreferences(username, MODE_PRIVATE)
            .getString("text10", " ")

        weightBP.setText(text1Org)
        repBP.setText(text2Org)
        weightSP.setText(text3Org)
        repSP.setText(text4Org)
        weightSquat.setText(text5Org)
        repSquat.setText(text6Org)
        weightDeadlift.setText(text7Org)
        repDeadlift.setText(text8Org)
        weightTRow.setText(text9Org)
        repTRow.setText(text10Org)

        continueStartingPoint.setOnClickListener {
            weightBP.onEditorAction(EditorInfo.IME_ACTION_DONE)
            weightSP.onEditorAction(EditorInfo.IME_ACTION_DONE)
            weightSquat.onEditorAction(EditorInfo.IME_ACTION_DONE)
            weightDeadlift.onEditorAction(EditorInfo.IME_ACTION_DONE)
            weightTRow.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repBP.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repSP.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repSquat.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repDeadlift.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repTRow.onEditorAction(EditorInfo.IME_ACTION_DONE)

            getSharedPreferences(username, MODE_PRIVATE)
                .edit()
                .putString("text1", weightBP.text.toString().trim())
                .putString("text2", repBP.text.toString().trim())
                .putString("text3", weightSP.text.toString().trim())
                .putString("text4", repSP.text.toString().trim())
                .putString("text5", weightSquat.text.toString().trim())
                .putString("text6", repSquat.text.toString().trim())
                .putString("text7", weightDeadlift.text.toString().trim())
                .putString("text8", repDeadlift.text.toString().trim())
                .putString("text9", weightTRow.text.toString().trim())
                .putString("text10", repTRow.text.toString().trim())
                .apply()

            sharedPrefMethod(username)

            continueSP()
        }

        backButtonSP.setOnClickListener{
            val intent = Intent(this, physiqueActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            startActivity(intent)
        }

    }

    private fun continueSP(){
        if (weightBP.text.toString().trim().isNotEmpty() && weightSP.text.toString().trim().isNotEmpty() && weightSquat.text.toString().trim().isNotEmpty()
            &&  weightDeadlift.text.toString().trim().isNotEmpty() && weightTRow.text.toString().trim().isNotEmpty()
            &&  repBP.text.toString().trim().isNotEmpty()
            &&  repSP.text.toString().trim().isNotEmpty() &&  repSquat.text.toString().trim().isNotEmpty()
            && repDeadlift.text.toString().trim().isNotEmpty()
            &&  repTRow.text.toString().trim().isNotEmpty()){
            calculateRep()

        }else{
            Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun calculateRep(){
        val repMaxBP: Double = 5*(Math.floor(((weightBP.text.toString().trim().toInt()) + (5*((repBP.text.toString().trim().toInt())/2))).toDouble())/5)
        val repMaxSP: Double = 5*(Math.floor(((weightSP.text.toString().trim().toInt()) + (5*((repSP.text.toString().trim().toInt())/2))).toDouble())/5)
        val repMaxSquat: Double = 5*(Math.floor(((weightSquat.text.toString().trim().toInt()) + (5*((repSquat.text.toString().trim().toInt())/2))).toDouble())/5)
        val repMaxDeadlift: Double = 5*(Math.floor(((weightDeadlift.text.toString().trim().toInt()) + (5*((repDeadlift.text.toString().trim().toInt())/2))).toDouble())/5)
        val repMaxTRow: Double = 5*(Math.floor(((weightTRow.text.toString().trim().toInt()) + (5*((repTRow.text.toString().trim().toInt())/2))).toDouble())/5)

        addContentToDB(repMaxBP, repMaxSP, repMaxSquat, repMaxDeadlift, repMaxTRow)


    }
    private fun sharedPrefMethod(username: String?){
        getSharedPreferences("secure", MODE_PRIVATE)
            .edit()
            .putBoolean("logged",true)
            .apply()
        getSharedPreferences("username", MODE_PRIVATE)
            .edit()
            .putString("user", username)
            .apply()
        getSharedPreferences(username, MODE_PRIVATE)
            .edit()
            .putBoolean("completeSetUp", true)
            .apply()
    }

    private fun addContentToDB(repMaxBP:Double, repMaxSP:Double, repMaxSquat:Double, repMaxDeadlift:Double, repMaxTRow:Double){

        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username!!)
/*        val startingPointInfo = hashMapOf("Max_Weight_BP" to weightBP.text.toString().trim(),
            "Max_Weight_SP" to weightSP.text.toString().trim(),
            "Max_Weight_Squat" to weightSquat.text.toString().trim(), "Max_Weight_Deadlift" to weightDeadlift.text.toString().trim(),
            "Max_Weight_TRow" to weightTRow.text.toString().trim(),
            "Max_Rep_BP" to repBP.text.toString().trim(), "Max_Rep_SP" to repSP.text.toString().trim(),
            "Max_Rep_Squat" to repSquat.text.toString().trim(), "Max_Rep_Deadlift" to repDeadlift.text.toString().trim(),
            "Max_Rep_TRow" to repTRow.text.toString().trim())*/
        val startingPointInfo = hashMapOf("repMaxBP" to repMaxBP.toString(), "repMaxSp" to repMaxSP.toString(),  "repMaxSquat" to repMaxSquat.toString(),
        "repMaxDeadlift" to repMaxDeadlift.toString(), "repMaxTRow" to repMaxTRow.toString())
        accountDB.update(startingPointInfo as Map<String, Any>)
        val intent = Intent(this, loadingActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("Activity", "SP");
        startActivity(intent)
        finish()
    }

}