package com.example.learningtolive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        FirebaseHandler fb = new FirebaseHandler();

        Category testCategory = new Category(Category.Name.DAILYLIFE, "test1") ;
        fb.setValue("testRef", testCategory);
        fb.getValue("testRef");

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> housing = new ArrayList<String>();
        housing.add("Finding a home");
        housing.add("Ageuk");
        housing.add("New furniture");

        List<String> food = new ArrayList<String>();
        food.add("BM stores");
        food.add("Wilko");
        food.add("Save the student");

        List<String> eatingOut = new ArrayList<String>();
        eatingOut.add("Where to eat in Bradford");
        eatingOut.add("Halal food");

        expandableListDetail.put("Housing and Accommodation", housing);
        expandableListDetail.put("Food and Home", food);
        expandableListDetail.put("Eating Out", eatingOut);
        return expandableListDetail;
    }
}
