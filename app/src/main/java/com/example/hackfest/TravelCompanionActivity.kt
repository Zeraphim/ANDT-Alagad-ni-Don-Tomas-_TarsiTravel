package com.example.hackfest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class TravelCompanionActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.travel_companion)

        val covprev = findViewById<ImageButton>(R.id.covid_prevention).setOnClickListener {
            onBackPressed()
            val intent : Intent = Intent(this, CovidPreventionActivity::class.java)
            startActivity(intent)
        }
        val covupdates = findViewById<ImageButton>(R.id.covid_updates).setOnClickListener {
            onBackPressed()
            val intent : Intent = Intent(this, CovidUpdatesActivity::class.java)
            startActivity(intent)
        }

        val navBar = findViewById<ImageView>(R.id.navBar).setOnClickListener {
            var intent : Intent = Intent(this, NavActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



}