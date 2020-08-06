package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.confirm_dialogue.view.*

class SettingsActivity: AppCompatActivity() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bundle: Bundle? = intent.extras
        username = bundle?.getString("username")

        changeButton.setOnClickListener {
            val intent = Intent(this, changeUserPass::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
        backButtonSettings.setOnClickListener {
            finish()
        }
        deleteButton.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.confirm_dialogue, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            mDialogView.cancelButton.setOnClickListener {
                mAlertDialog.dismiss()
            }

            mDialogView.confirmButton.setOnClickListener {
                val db = Firebase.firestore
                val accountDB = db.collection("Account").document(username!!)
                accountDB.delete()
                mAlertDialog.dismiss()
                val intent = Intent(this, Signin::class.java)
                startActivity(intent)

            }
        }

        editProfileButton.setOnClickListener {
            val db = Firebase.firestore
            var password: String
            val accountDB = db.collection("Account").document(username.toString())
            accountDB.get()
                .addOnSuccessListener { document ->
                    password = document.data?.get("password") as String
                    accountDB.delete()
                    val accountData = hashMapOf("password" to password)
                    accountDB.set(accountData)
                }

            val intent = Intent(this, bodyInformation::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()
        }

        signoutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signOut(){
        getSharedPreferences("secure", MODE_PRIVATE)
            .edit()
            .putBoolean("logged",false)
            .apply()
        val intent = Intent(this, Signin::class.java)
        startActivity(intent)
    }


}