package com.evolveworkoutapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_changeuserpass.*
import kotlinx.android.synthetic.main.confirm_dialogue.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class changeUserPass: AppCompatActivity() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changeuserpass)
        val bundle: Bundle? = intent.extras
        username = bundle?.getString("username")
        newUsername.setText(username)



        savePassword.setOnClickListener {
            newUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
            newPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
            confirmPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)

            if (L1Reveal.visibility==View.VISIBLE && L2Reveal.visibility==View.VISIBLE){
                if (newPassword.text.toString().trim().isNotEmpty() && confirmPassword.text.toString().trim().isNotEmpty()){
                    UpdateInformation()
                }
                else{
                    Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
                }

            }
        }

        backButtonUpdate.setOnClickListener {
            finish()
        }
    }



    private fun UpdateInformation(){
        val password1 : String = newPassword.text.toString().trim()
        val password2 : String = confirmPassword.text.toString().trim()

        if (newPassword.text.toString().trim().isNotEmpty() && confirmPassword.text.toString().trim().isNotEmpty()){
                if (password1==password2){
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
                        accountDB.update("password", password1)
                        mAlertDialog.dismiss()
                        finish()
                    }

                }else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }

    }
}

