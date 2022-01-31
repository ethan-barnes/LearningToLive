package com.example.learningtolive

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class FirebaseHandler {
    private val TAG = "FirebaseHandler"
    private val firebaseUrl = "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseUrl)

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
