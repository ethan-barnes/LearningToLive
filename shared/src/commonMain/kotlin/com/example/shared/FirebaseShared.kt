package com.example.shared

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow

// https://github.com/GitLiveApp/firebase-kotlin-sdk

class FirebaseShared {
    private val firebaseUrl =
    "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db: FirebaseDatabase = Firebase.database(firebaseUrl)

    fun getDataFlow(country: String, category: String, ref: String): Flow<DataSnapshot> {
        var path = "$country/$category/$ref"
        val request = db.reference(path)
        return request.valueEvents
    }

}