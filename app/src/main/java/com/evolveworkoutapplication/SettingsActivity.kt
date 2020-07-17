package com.evolveworkoutapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

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
            finish()
        }

    }

}