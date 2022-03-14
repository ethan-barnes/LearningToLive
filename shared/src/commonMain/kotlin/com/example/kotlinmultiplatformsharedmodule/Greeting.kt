package com.example.kotlinmultiplatformsharedmodule

class reeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}