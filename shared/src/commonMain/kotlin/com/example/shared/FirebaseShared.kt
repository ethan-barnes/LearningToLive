package com.example.shared

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database

class FirebaseShared {
    private val firebaseUrl =
    "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db: FirebaseDatabase = Firebase.database(firebaseUrl)

     suspend fun test() {
        val myRef = db.reference("test")
         myRef.setValue("testVal", true)
    }
}