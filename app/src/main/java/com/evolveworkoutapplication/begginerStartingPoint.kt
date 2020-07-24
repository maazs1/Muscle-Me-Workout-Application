package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.acitivity_begginerstartingpoint.*
import kotlinx.android.synthetic.main.activity_startpoint.*
import kotlinx.android.synthetic.main.activity_startpoint.backButtonSP
import kotlinx.android.synthetic.main.activity_startpoint.continueStartingPoint
import kotlinx.android.synthetic.main.activity_startpoint.repSquat

class begginerStartingPoint : AppCompatActivity() {

    private var username: String?=null
    private var gender: String?=null
    private var age: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_begginerstartingpoint)

        val bundle: Bundle? = intent.extras
        username= bundle?.getString("username")
        age = bundle?.getString("age")
        gender= bundle?.getString("gender")

        val pushupsV = getSharedPreferences(username, MODE_PRIVATE)
            .getString("textNew1", " ")
        val pullupsV = getSharedPreferences(username, MODE_PRIVATE)
            .getString("textNew2", " ")
        val BSquatsV = getSharedPreferences(username, MODE_PRIVATE)
            .getString("textNew3", " ")
        val TRXRowV = getSharedPreferences(username, MODE_PRIVATE)
            .getString("textNew4", " ")
        val CrunchV = getSharedPreferences(username, MODE_PRIVATE)
            .getString("textNew5", " ")


        repPushup.setText(pushupsV)
        repPullUps.setText(pullupsV)
        repSquat2.setText(BSquatsV)
        repTRXrow.setText(TRXRowV)
        repCrunch.setText(CrunchV)


        continueStartingPoint.setOnClickListener {
            repPushup.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repPullUps.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repSquat2.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repTRXrow.onEditorAction(EditorInfo.IME_ACTION_DONE)
            repCrunch.onEditorAction(EditorInfo.IME_ACTION_DONE)


            getSharedPreferences(username, MODE_PRIVATE)
                .edit()
                .putString("textNew1", repPushup.text.toString().trim())
                .putString("textNew2", repPullUps.text.toString().trim())
                .putString("textNew3", repSquat2.text.toString().trim())
                .putString("textNew4", repTRXrow.text.toString().trim())
                .putString("textNew5", repCrunch.text.toString().trim())
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
        if (repPushup.text.toString().trim().isNotEmpty() && repPullUps.text.toString().trim().isNotEmpty() && repSquat2.text.toString().trim().isNotEmpty()
            &&  repTRXrow.text.toString().trim().isNotEmpty() && repCrunch.text.toString().trim().isNotEmpty()){

            addContentToDB()

        }else{
            Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
        }
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

    private fun addContentToDB(){

        val db = Firebase.firestore
        val accountDB = db.collection("Account").document(username!!)
        val beginnerStartingPointInfo = hashMapOf("Max_PushUps" to repPushup.text.toString().trim(),
            "Max_PullUps" to repPullUps.text.toString().trim(),
            "Max_SquatB" to repSquat2.text.toString().trim(), "Max_TRX" to repTRXrow.text.toString().trim(),
            "Max_Crunch" to repCrunch.text.toString().trim())
        accountDB.update(beginnerStartingPointInfo as Map<String, Any>)
        val intent = Intent(this, loadingActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("Activity", "SP");
        startActivity(intent)
        finish()
    }

}