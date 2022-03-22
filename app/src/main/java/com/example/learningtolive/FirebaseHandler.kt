package com.example.learningtolive

import android.util.Log
import com.example.shared.MyCallback
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.HashMap

class FirebaseHandler {
    private val TAG = "FirebaseHandler"
    private val firebaseUrl = "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/"
    private val db: FirebaseDatabase = FirebaseDatabase.getInstance(firebaseUrl)

    fun getHeadings(country: String, category: String, idOrName: Boolean): Array<String> {
        var path = "$country/$category"
        var r = arrayOf<String>()
        val ref = db.getReference(path)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = arrayListOf<String>()
                for (ds in dataSnapshot.children) {
                    if (ds.key != null) {
                        if (idOrName) { // true = id
                            data.add(ds.child("id").value.toString())
                        } else { // false = name
                            data.add(ds.child("name").value.toString())
                        }
                    }
                }
                data.toArray(r)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException())
            }
        })

        while (r.isEmpty()) {
            // wait for firebase
        }
        return r
    }

    fun getReferences() {

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
