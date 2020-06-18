package com.evolveworkoutapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.animation.AlphaAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*

class Signin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        signup_button.setOnClickListener {
            username_editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            password_editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            signIn()
        }

        login_button.setOnClickListener {
            username_editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            password_editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
            if ((username_editText.text.toString().trim().isNotEmpty()) && (password_editText.text.toString().trim().isNotEmpty())){
                if (login_button.text=="SIGNUP NOW"){
                    checkExistingAccount()
                }
                else if (login_button.text=="LOGIN NOW"){
                    loginUser()
                }
            }else{
                errorField.setText("Missing Username or Password")
                errorview()
            }
        }
    }

    private fun checkExistingAccount(){
        val db = Firebase.firestore
        val username = username_editText.text.toString().trim()
        val accountDB = db.collection("Account").document(username)
        accountDB.get()
            .addOnSuccessListener { document ->
                if (document!=null && document.exists()){
                    errorField.setText("Username already exists")
                    errorview()
                }else{

                    val accountData = hashMapOf("password" to password_editText.text.toString().trim())
                    accountDB.set(accountData)
                    val intent = Intent(this, bodyInformation::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    finish()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", exception.toString())
            }
    }

    private fun signIn() {
       // errorField.visibility=View.INVISIBLE
        username_editText.setText("")
        password_editText.setText("")
        if (login_text.text=="Login"){
            login_text.setText("SignUp")
            signup_button.setText("LOGIN")
            login_button.setText("SIGNUP NOW")
            login_button.setBackgroundResource(R.drawable.greenbutton_bg)
        }else if (login_text.text=="SignUp"){
            login_text.setText("Login")
            signup_button.setText("SIGNUP")
            login_button.setText("LOGIN NOW")
            login_button.setBackgroundResource(R.drawable.redbutton)
        }
    }

    private fun loginUser(){
        val db = Firebase.firestore
        val username = username_editText.text.toString().trim()
        val accountDB = db.collection("Account").document(username)
        accountDB.get()
            .addOnSuccessListener { document ->
                if(document.id == username_editText.text.toString() && document.data?.get("password") == password_editText.text.toString()) {


                    val intent = Intent(this, bodyInformation::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    finish()
                }else{
                    errorField.setText("Invalid Username or Password")
                    errorview()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", exception.toString())
            }
    }


    fun errorview(){
        errorField.visibility=View.VISIBLE
        errorField.postDelayed(Runnable {
            run(){
                errorField.visibility=View.INVISIBLE
            }
        }, 3000)
    }

}