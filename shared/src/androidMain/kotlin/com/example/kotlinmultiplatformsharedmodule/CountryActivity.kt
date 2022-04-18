package com.example.kotlinmultiplatformsharedmodule

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CountryActivity : AppCompatActivity() {
    lateinit var country: String
    private lateinit var dailyLifeButton: Button
    private lateinit var healthButton: Button
    private lateinit var settlingButton: Button
    private lateinit var migrantButton: Button
    private lateinit var languageButton: Button
    private lateinit var rateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        if (extras != null) {
            country = extras.getString("country")!!
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        dailyLifeButton = findViewById<View>(R.id.daily_life_button) as Button
        dailyLifeButton.setOnClickListener {
            val c = Category(Category.Name.DAILYLIFE)
            sendCategory(c, country)
        }
        healthButton = findViewById<View>(R.id.health_button) as Button
        healthButton.setOnClickListener {
            val c = Category(Category.Name.HEALTH)
            sendCategory(c, country)
        }
        settlingButton = findViewById<View>(R.id.settling_button) as Button
        settlingButton.setOnClickListener {
            val c = Category(Category.Name.SETTLINGIN)
            sendCategory(c, country)
        }
        migrantButton = findViewById<View>(R.id.migrant_button) as Button
        migrantButton.setOnClickListener {
            val c = Category(Category.Name.MIGRANTSTATUS)
            sendCategory(c, country)
        }
        languageButton = findViewById<View>(R.id.language_button) as Button
        languageButton.setOnClickListener {
            val c = Category(Category.Name.LANGUAGE)
            sendCategory(c, country)
        }
        rateButton = findViewById<View>(R.id.contact_button) as Button
        rateButton.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.rate_url)))
                this.startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "Cannot open link, missing browser on device.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun sendCategory(category: Category, country: String) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("category", category)
        intent.putExtra("country", country)
        startActivity(intent)
    }
}
