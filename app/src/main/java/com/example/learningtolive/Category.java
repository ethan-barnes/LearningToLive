package com.example.learningtolive;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Category implements Serializable {

    enum Name {
        DAILYLIFE,
        HEALTH,
        SETTLINGIN,
        MIGRANTSTATUS,
        LANGUAGE
    }

    Name name;
    String subCategories;

    public Category(Name n) {
        name = n;
    }

    public Category(Name n, String sc) {
        name = n;
        subCategories = sc;
    }
}
