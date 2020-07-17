package com.evolveworkoutapplication

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_changeuserpass.*

class changeUserPass: AppCompatActivity() {

    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changeuserpass)

        changePassword.setOnClickListener {
            L1Reveal.setVisibility(View.VISIBLE);
            L2Reveal.setVisibility(View.VISIBLE);
        }
    }
}