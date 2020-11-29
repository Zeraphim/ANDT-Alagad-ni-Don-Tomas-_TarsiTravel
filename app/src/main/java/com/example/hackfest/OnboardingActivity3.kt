package com.example.hackfest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class OnboardingActivity3: AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding3);

        val next1 = findViewById<Button>(R.id.button).setOnClickListener {
            var intent: Intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}