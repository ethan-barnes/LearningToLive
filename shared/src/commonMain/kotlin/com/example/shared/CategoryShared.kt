package com.example.shared

// import java.io.Serializable

class CategoryShared /*: Serializable*/ {
    enum class Name {
        DAILYLIFE, HEALTH, SETTLINGIN, MIGRANTSTATUS, LANGUAGE
    }

    var name: Name

    constructor(n: Name) {
        name = n
    }
}