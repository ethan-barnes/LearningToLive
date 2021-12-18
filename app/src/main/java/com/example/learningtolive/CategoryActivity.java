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
        // Category object is used to control what links to display in expandable list.
        if (extras != null) {
            category = (Category) getIntent().getSerializableExtra("category");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        createCategoriesLists(this, category);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        ExpandableListDataPump.populateLists(this, getApplicationContext());
    }

    /***
     * Creates expandable list and handles opening of URLs. Called for each heading in FireBase.
     * @param categoryActivity used to create browser activity.
     * @param expandableListDetail HashMap that contains headings and list of URL names from FireBase.
     * @param urls List of URLs taken from FireBase
     * @param context static method needs context to create toast.
     */
    public static void updateLists(CategoryActivity categoryActivity,
                                   HashMap<String, List<String>> expandableListDetail,
                                   HashMap<String, String> urls,
                                   Context context) {
        /* Stores our URls and URL text in the object. Headings are taken one at a time from FireBase
        so this information needs to be stored here so it can all be displayed at once. */
        activityExpandableListDetail.putAll(expandableListDetail);
        activityUrls.putAll(urls);

        // Creating the expandable list.
        expandableListTitle = new ArrayList<String>(activityExpandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(categoryActivity, expandableListTitle,
                activityExpandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        // Attempts to open the URL in phone's browser, catches exceptions if it cannot.
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

    /***
     * Used to translate FireBase reference into human-readable heading text.
     * @param reference FireBase reference.
     * @return Human-readable heading that is mapped to FireBase reference.
     */
    public static String getSubCategory(String reference) {
        return categories.get(reference);
    }

    /***
     * Sets values that are used by FirebaseHandler to create links in expandable list.
     * @param context static method needs context to get class properties.
     * @param category is used to select correct links from firebase.
     */
    private static void createCategoriesLists(Context context, Category category) {
        String[] headings = {};

        // Clear HashMaps to prevent irrelevant links being left over from previously visited pages.
        activityExpandableListDetail.clear();
        activityUrls.clear();

        // Load relevant values from firebase.xml.
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

        // Provides mapping between FireBase reference name and human-readable heading in app.
        if (headings.length == references.length) {
            for(int i = 0; i < headings.length; i++) {
                categories.put(references[i], headings[i]);
            }
        }
    }

}
