package com.example.hackfest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MotionEventCompat

class CityDetails : Activity(){
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        val recvAddress = intent.getStringExtra("ADDRESS")
        findViewById<TextView>(R.id.mainAddress).text = recvAddress
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setDimAmount(0.0f)

    }
}