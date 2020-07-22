package com.evolveworkoutapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_changeuserpass.*
import kotlinx.android.synthetic.main.confirm_dialogue.view.*
import android.view.inputmethod.EditorInfo



class changeUserPass: AppCompatActivity() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changeuserpass)
        val bundle: Bundle? = intent.extras
        username = bundle?.getString("username")
        newUsername.setText(username)


        changePassword.setOnClickListener {

            newPassword.text.clear()
            confirmPassword.text.clear()

            if (L1Reveal.visibility==View.INVISIBLE){
                L1Reveal.setVisibility(View.VISIBLE);
                L2Reveal.setVisibility(View.VISIBLE);
            }else{
                L1Reveal.setVisibility(View.INVISIBLE);
                L2Reveal.setVisibility(View.INVISIBLE);

            }
        }

        savePassword.setOnClickListener {
            newUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
            newPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
            confirmPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)

            if (newPassword.text.toString().trim().isNotEmpty() && confirmPassword.text.toString().trim().isNotEmpty()){
                UpdateInformation()
            }
        }
    }

    private fun UpdateInformation(){
        val password1 : String = newPassword.text.toString().trim()
        val password2 : String = confirmPassword.text.toString().trim()

        if ((password1==password2) && (password1!="") && (password2!="")){
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.confirm_dialogue, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            mDialogView.cancelButton.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }
    }
}

