package com.example.hackfest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class NavActivity : Activity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setDimAmount(0.0f)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

        val homeBtn = findViewById<Button>(R.id.homeBtn).setOnClickListener {
            onBackPressed()
        }
        val univBtn = findViewById<Button>(R.id.univBtn).setOnClickListener {
            val intent : Intent = Intent(this@NavActivity, UnivActivity::class.java)
            startActivity(intent)
        }
        val travelBtn = findViewById<Button>(R.id.travelBtn).setOnClickListener {
            onBackPressed()
        }
        val settingsBtn = findViewById<Button>(R.id.settingsBtn).setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}