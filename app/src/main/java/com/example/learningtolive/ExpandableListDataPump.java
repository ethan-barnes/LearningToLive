package com.example.learningtolive;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    private final static String TAG = "ExpandableListDataPump";

    /***
     * This a more logical function name than getData()
     * @param categoryActivity used to work around asynchronous issues.
     * @param context static method needs context.
     */
    public static void populateLists(CategoryActivity categoryActivity, Context context) {
        getData(categoryActivity, context);
    }

    /***
     * Sends request to FireBase for each heading in the selected page. Then calls createLists() to
     * send this information back to CategoryActivity.
     * @param categoryActivity used to work around asynchronous issues.
     * @param context static method needs context.
     */
    public static void getData(CategoryActivity categoryActivity, Context context) {
        FirebaseHandler fb = new FirebaseHandler();
        String[] refs = categoryActivity.references;

        try {
            for (String ref : refs) {
                fb.getValue(ref, new MyCallback() {
                    @Override
                    // Callback used to return value from asynchronous FireBase requests.
                    public void onCallBack(String title, HashMap value) {
                        if (!value.isEmpty()) { // Some headings may not be stored in FireBase.
                            createLists(title, value, categoryActivity, context);
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not get from FireBase. " + e);
        }

    }

    /***
     * Takes values from FireBase requests, formats them, and sends them to CategoryActivity to
     * create expandable list.
     * @param title Heading that will be displayed in expandable list.
     * @param hash a hashmap created by FireBase handler of URL name and URL.
     * @param categoryActivity the categoryActivity object that will be displaying our information.
     * @param context static method needs context.
     */
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
