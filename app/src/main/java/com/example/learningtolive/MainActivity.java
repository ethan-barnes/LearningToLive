package com.example.learningtolive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button ukButton;
    Button spainButton;
    Button irelandButton;
    Button sloveniaButtion;
    Button finlandButton;
    //public static final String COUNTRY_NAME = "com.example.learningtolive.Country";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ukButton = (Button) findViewById(R.id.uk_button);
        ukButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Country c = new Country(Country.Name.UNITEDKINGDOM);
                selectCountry(v, c);
            }
        });
        spainButton = (Button) findViewById(R.id.spain_button);
        spainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Country c = new Country(Country.Name.SPAIN);
                selectCountry(v, c);
            }
        });
        irelandButton = (Button) findViewById(R.id.ireland_button);
        irelandButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Country c = new Country(Country.Name.IRELAND);
                selectCountry(v, c);
            }
        });
        sloveniaButtion = (Button) findViewById(R.id.slovenia_button);
        sloveniaButtion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Country c = new Country(Country.Name.SLOVENIA);
                selectCountry(v, c);
            }
        });
        finlandButton = (Button) findViewById(R.id.finland_button);
        finlandButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Country c = new Country(Country.Name.FINLAND);
                selectCountry(v, c);
            }
        });
    }

    // Called when country button is pressed
    private void selectCountry(View view, Country country) {
        Intent intent = new Intent(this, CountryActivity.class);
        switch (country.getName()) {
            case UNITEDKINGDOM:
                intent.putExtra("country", this.getResources().getString(R.string.united_kingdom));
                break;
            case SPAIN:
                intent.putExtra("country", this.getResources().getString(R.string.spain));
                break;
            case IRELAND:
                intent.putExtra("country", this.getResources().getString(R.string.ireland));
                break;
            case SLOVENIA:
                intent.putExtra("country", this.getResources().getString(R.string.slovenia));
                break;
            case FINLAND:
                intent.putExtra("country", this.getResources().getString(R.string.finland));
                break;
        }
        startActivity(intent);
    }
}