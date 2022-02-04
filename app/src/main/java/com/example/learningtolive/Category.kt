package com.example.learningtolive

import java.io.Serializable

class Category : Serializable {
    enum class Name {
        DAILYLIFE, HEALTH, SETTLINGIN, MIGRANTSTATUS, LANGUAGE
    }

    @JvmField
    var name: Name
    var subCategories: String? = null

    constructor(n: Name) {
        name = n
    }

    constructor(n: Name, sc: String?) {
        name = n
        subCategories = sc
    }
}