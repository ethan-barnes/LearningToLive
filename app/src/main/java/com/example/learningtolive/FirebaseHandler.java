package com.example.learningtolive;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHandler {
    private final String TAG = "FirebaseHandler";
    private String firebaseUrl = "https://learningtolive-e4844-default-rtdb.europe-west1.firebasedatabase.app/";
    FirebaseDatabase db;

    public FirebaseHandler() {
        db = FirebaseDatabase.getInstance(firebaseUrl);
    }

    public void setValue(String reference, Object value) {
        DatabaseReference ref = db.getReference(reference);
        ref.setValue(value);
    }

    public String getValue(String reference) {
        DatabaseReference ref = db.getReference(reference);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
        return "";
    }

}
