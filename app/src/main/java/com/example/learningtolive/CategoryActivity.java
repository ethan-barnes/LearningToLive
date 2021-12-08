package com.example.learningtolive;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

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
        /*
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
        */

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle,
                expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded. ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener(){
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed. ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

    }
}
