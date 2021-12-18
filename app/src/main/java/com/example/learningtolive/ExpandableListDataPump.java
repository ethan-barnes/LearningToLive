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
        String[] refs = categoryActivity.references;

        try {
            for (String ref : refs) {
                fb.getValue(ref, new MyCallback() {
                    @Override
                    public void onCallBack(String title, HashMap value) {
                        if (!value.isEmpty()) {
                            Log.d(TAG, value.toString());
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

        List<String> list = new ArrayList<>();
        for (String key : hash.keySet()) {
            list.add(key);
            urls.put(key, hash.get(key));
        }

        expandableListDetail.put(categoryActivity.getSubCategory(title), list);
        categoryActivity.updateLists(categoryActivity, expandableListDetail, urls, context);
    }
}
