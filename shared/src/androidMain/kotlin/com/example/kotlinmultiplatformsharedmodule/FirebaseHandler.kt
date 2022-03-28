package com.example.kotlinmultiplatformsharedmodule

import android.content.Context
import android.util.Log
import com.example.kotlinmultiplatformsharedmodule.ExpandableListDataPump.populateLists
import com.example.shared.MyCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.HashMap

class FirebaseHandler {
    private val TAG = "FirebaseHandler"
    private val firebaseUrl =
        "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseUrl)

    fun getCategories(
        categoryActivity: CategoryActivity,
        categoryCompanion: CategoryActivity.Companion,
        context: Context,
        country: String,
        category: String
    ) {
        var path = "$country/$category/headings"
        val ref = db.getReference(path)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val headings = arrayListOf<String>()
                val references = arrayListOf<String>()

                for (ds in dataSnapshot.children) {
                    if (ds.key != null) {
                        references.add(ds.child("id").value.toString())
                        headings.add(ds.child("name").value.toString())
                    }
                }

                categoryCompanion.references = references.toTypedArray()
                categoryCompanion.headings = headings.toTypedArray()

                categoryCompanion.setCategories(country, category)
                populateLists(categoryActivity, category, context)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException())
            }
        })
    }

    /***
     * Unused method, sets value in FireBase.
     * @param reference name of FireBase reference.
     * @param value string value.
     */
    fun setValue(reference: String, value: Any) {
        val ref = db.getReference(reference)
        ref.setValue(value)
    }

    /***
     * Gets value from FireBase.
     * @param reference the FireBase reference to be requested.
     * @param myCallback used to handle asynchronous calls of FireBase.
     */
    fun getValue(reference: String, myCallback: MyCallback) {
        val ref = db.getReference(reference)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = HashMap<String?, String?>()
                for (ds in dataSnapshot.children) {
                    if (ds.key != null) {
                        data[ds.key] = ds.value.toString()
                    }
                }
                // Sends data back to ExpandableListDataPump.
                myCallback.onCallBack(reference, data)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException())
            }
        })
    }
}
