package com.example.learningtolive;

import java.util.ArrayList;

public class Category {

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
