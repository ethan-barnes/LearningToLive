package com.example.learningtolive;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    private final static String TAG = "ExpandableListDataPump";

    public static void populateLists(CategoryActivity categoryActivity, Context context) {
        getData(categoryActivity, context);
    }

    public static void getData(CategoryActivity categoryActivity, Context context) {
        FirebaseHandler fb = new FirebaseHandler();
        String[] refs = context.getResources().getStringArray(R.array.daily_life_references);

        try {
            for (String ref : refs) {
                fb.getValue(ref, new MyCallback() {
                    @Override
                    public void onCallBack(String title, HashMap value, Boolean hasValue) {
                        Log.d(TAG, value.toString());
                        if (hasValue) {
                            createLists(title, value, categoryActivity, context);
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not get from FireBase. " + e);
        }

    }

    private static void createLists(String title, HashMap<String, String> hash, CategoryActivity categoryActivity, Context context) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        HashMap<String, String> urls = new HashMap<>();

        List<String> housing = new ArrayList<String>();
        for (String key : hash.keySet()) {
            housing.add(key);
            urls.put(key, hash.get(key));
        }

        expandableListDetail.put(title, housing);
        categoryActivity.updateLists(categoryActivity, expandableListDetail, urls, context);
    }
}
