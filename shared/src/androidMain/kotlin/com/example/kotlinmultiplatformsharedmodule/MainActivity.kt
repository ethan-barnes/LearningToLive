package com.example.kotlinmultiplatformsharedmodule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.shared.*
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var ukButton: Button
    private lateinit var spainButton: Button
    private lateinit var irelandButton: Button
    private lateinit var sloveniaButtion: Button
    private lateinit var finlandButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ukButton = findViewById<View>(R.id.uk_button) as Button
        ukButton.setOnClickListener { v ->
            val c = Country(Country.Name.UNITEDKINGDOM)
            selectCountry(v, c)
        }
        spainButton = findViewById<View>(R.id.spain_button) as Button
        spainButton.setOnClickListener { v ->
            val c = Country(Country.Name.SPAIN)
            selectCountry(v, c)
        }
        irelandButton = findViewById<View>(R.id.ireland_button) as Button
        irelandButton.setOnClickListener { v ->
            val c = Country(Country.Name.IRELAND)
            selectCountry(v, c)
        }
        sloveniaButtion = findViewById<View>(R.id.slovenia_button) as Button
        sloveniaButtion.setOnClickListener { v ->
            val c = Country(Country.Name.SLOVENIA)
            selectCountry(v, c)
        }
        finlandButton = findViewById<View>(R.id.finland_button) as Button
        finlandButton.setOnClickListener { v ->
            var x : FirebaseShared = FirebaseShared()
            GlobalScope.launch(Unconfined) {
                x.getHeadings("united_kingdom", "health")
            }

            val c = Country(Country.Name.FINLAND)
            selectCountry(v, c)
        }
    }

    // Called when country button is pressed
    private fun selectCountry(view: View, country: Country) {
        val intent = Intent(this, CountryActivity::class.java)
        when (country.name) {
            Country.Name.UNITEDKINGDOM -> intent.putExtra(
                "country",
                this.resources.getString(R.string.united_kingdom)
            )
            Country.Name.SPAIN -> intent.putExtra(
                "country",
                this.resources.getString(R.string.spain)
            )
            Country.Name.IRELAND -> intent.putExtra(
                "country",
                this.resources.getString(R.string.ireland)
            )
            Country.Name.SLOVENIA -> intent.putExtra(
                "country",
                this.resources.getString(R.string.slovenia)
            )
            Country.Name.FINLAND -> intent.putExtra(
                "country",
                this.resources.getString(R.string.finland)
            )
        }
        startActivity(intent)
    }
}
