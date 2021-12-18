package com.example.learningtolive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CountryActivity extends AppCompatActivity {
    String country;
    Button dailyLifeButton;
    Button healthButton;
    Button settlingButton;
    Button migrantButton;
    Button languageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            country = extras.getString("country");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        TextView countryNameTV = (TextView) findViewById(R.id.countryNameTV);
        countryNameTV.setText(country);

        dailyLifeButton = (Button) findViewById(R.id.daily_life_button);
        dailyLifeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category c = new Category(Category.Name.DAILYLIFE);
                sendCategory(v, c);
            }
        });

        healthButton = (Button) findViewById(R.id.health_button);
        healthButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category c = new Category(Category.Name.HEALTH);
                sendCategory(v, c);
            }
        });

        settlingButton = (Button) findViewById(R.id.settling_button);
        settlingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category c = new Category(Category.Name.SETTLINGIN);
                sendCategory(v, c);
            }
        });

        migrantButton = (Button) findViewById(R.id.migrant_button);
        migrantButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category c = new Category(Category.Name.MIGRANTSTATUS);
                sendCategory(v, c);
            }
        });

        languageButton = (Button) findViewById(R.id.language_button);
        languageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category c = new Category(Category.Name.LANGUAGE);
                sendCategory(v, c);
            }
        });
    }

    private void sendCategory(View view, Category category) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}