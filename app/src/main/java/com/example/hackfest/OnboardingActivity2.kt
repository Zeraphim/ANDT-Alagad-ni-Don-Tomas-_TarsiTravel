package com.example.hackfest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class OnboardingActivity2: AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding2)

        val next3 = findViewById<Button>(R.id.button).setOnClickListener {
            var intent: Intent = Intent(this, OnboardingActivity3::class.java)
            startActivity(intent)
        }
    }
}