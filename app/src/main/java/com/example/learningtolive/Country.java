package com.example.learningtolive;

public class Country {

    enum Name {
        UNITEDKINGDOM,
        SPAIN,
        IRELAND,
        SLOVENIA,
        FINLAND
    }

    Name name;

    public Country(Name n) {
        name = n;
    }
}
