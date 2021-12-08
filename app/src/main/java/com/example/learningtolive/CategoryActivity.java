package com.example.learningtolive;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    // Will be stored in database in future
    private final LinkedList<String> lifeList = new LinkedList<>();
    private final LinkedList<String> housingList = new LinkedList<>();
    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("category");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String[] lifeCategories = {
                this.getResources().getString(R.string.life_housing),
                this.getResources().getString(R.string.life_food),
                this.getResources().getString(R.string.life_eating),
                this.getResources().getString(R.string.life_money),
                this.getResources().getString(R.string.life_family),
                this.getResources().getString(R.string.life_travel),
                this.getResources().getString(R.string.life_hospitals),
                this.getResources().getString(R.string.life_local)
        };
        for (int i = 0; i < lifeCategories.length; i++) {
            lifeList.addLast(lifeCategories[i]);
        }

        String[] housing = {
                "New furniture",
                "More placeholder text",
                "Ageuk"
        };
        for (int j = 0; j < housing.length; j++) {
            housingList.addLast(housing[j]);
        }


        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, lifeList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
