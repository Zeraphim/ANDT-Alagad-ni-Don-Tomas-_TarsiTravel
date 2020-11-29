package com.example.hackfest

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class CovidPreventionActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_prevention)

        val navBar = findViewById<ImageView>(R.id.navBar).setOnClickListener {
            var intent : Intent = Intent(this, TravelCompanionActivity::class.java)
            startActivity(intent)
        }
    }
}