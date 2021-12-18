package com.example.learningtolive;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private static String TAG = "CategoryActivity";
    static ExpandableListView expandableListView;
    static ExpandableListAdapter expandableListAdapter;
    static List<String> expandableListTitle;
    static private HashMap<String, List<String>> activityExpandableListDetail = new HashMap<>();
    static private HashMap<String, String> activityUrls = new HashMap<>();
    static private HashMap<String, String> categories = new HashMap<>();

    static public String[] references = {};
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = (Category) getIntent().getSerializableExtra("category");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        createCategoriesLists(this, category);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        ExpandableListDataPump.populateLists(this, getApplicationContext());
    }

    public static void updateLists(CategoryActivity categoryActivity,
                                   HashMap<String, List<String>> expandableListDetail,
                                   HashMap<String, String> urls,
                                   Context context) {
        activityExpandableListDetail.putAll(expandableListDetail);
        activityUrls.putAll(urls);


        expandableListTitle = new ArrayList<String>(activityExpandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(categoryActivity, expandableListTitle,
                activityExpandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                String url =  activityUrls.get(activityExpandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                Log.d(TAG, "URL: " + url);
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    categoryActivity.startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Cannot open link, missing browser on device.", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error opening link: " + e);
                } catch (ParseException pe) {
                    Toast.makeText(context, "Cannot parse link.", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error parsing link: " + pe);
                }
                return false;
            }
        });
    }

    public static String getSubCategory(String reference) {
        return categories.get(reference);
    }

    private static void createCategoriesLists(Context context, Category category) {
        String[] headings = {};
        activityExpandableListDetail.clear();
        activityUrls.clear();

        switch (category.name){
            case DAILYLIFE:
                headings = context.getResources().getStringArray(R.array.daily_life_headings);
                references = context.getResources().getStringArray(R.array.daily_life_references);
                break;
            case HEALTH:
                headings = context.getResources().getStringArray(R.array.health_headings);
                references = context.getResources().getStringArray(R.array.health_references);
                break;
            case SETTLINGIN:
                break;
            case MIGRANTSTATUS:
                break;
            case LANGUAGE:
                break;
        }

        if (headings.length == references.length) {
            for(int i = 0; i < headings.length; i++) {
                categories.put(references[i], headings[i]);
            }
        }
    }

}
