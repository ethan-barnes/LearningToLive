package com.example.learningtolive;

public class Category {

    enum Name {
        DAILYLIFE,
        HEALTH,
        SETTLINGIN,
        MIGRANTSTATUS,
        LANGUAGE
    }

    Name name;

    public Category(Name n) { name = n; }
}
