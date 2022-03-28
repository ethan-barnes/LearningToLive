package com.example.learningtolive

import java.io.Serializable

class Category : Serializable {
    enum class Name {
        DAILYLIFE, HEALTH, SETTLINGIN, MIGRANTSTATUS, LANGUAGE
    }

    var name: Name

    constructor(n: Name) {
        name = n
    }
}