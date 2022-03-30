package com.example.shared

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

// https://github.com/GitLiveApp/firebase-kotlin-sdk

class FirebaseShared {
    private val firebaseUrl =
    "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db: FirebaseDatabase = Firebase.database(firebaseUrl)

     suspend fun getHeadings(country: String, category: String) {
         var path = "$country/$category/headings"
         val myRef = db.reference(path)

         var f = myRef.valueEvents
         f.collect { response ->
             val headings = arrayListOf<String>()
             val references = arrayListOf<String>()

             for (ds in response.children) {
                 if (ds.key != null) {
                     var heading = ds.children.iterator()
                     references.add(heading.next().value()) // id
                     headings.add(heading.next().value()) // name
                 }
             }
             var x = true
         }
    }

}