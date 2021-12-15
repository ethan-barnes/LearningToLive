package com.example.learningtolive;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    private final static String TAG = "ExpandableListDataPump";

    public static void PopulateLists(CategoryActivity categoryActivity, Context context) {
        GetData(categoryActivity, context);
    }

    public static HashMap<String, List<String>> GetData(CategoryActivity categoryActivity, Context context) {
        FirebaseHandler fb = new FirebaseHandler();

        fb.getValue("housing", new MyCallback() {
            @Override
            public void onCallBack(HashMap value) {
                Log.d(TAG, value.toString());
                CreateLists(value, categoryActivity, context);
            }
        });
        return new HashMap<String, List<String>>();
    }

    private static void CreateLists(HashMap<String, String> hash, CategoryActivity categoryActivity, Context context) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> housing = new ArrayList<String>();
        for (String key : hash.keySet()) {
            housing.add(key);
        }

        expandableListDetail.put("Housing and Accommodation", housing);
        categoryActivity.updateLists(categoryActivity, expandableListDetail, context);
    }
}
