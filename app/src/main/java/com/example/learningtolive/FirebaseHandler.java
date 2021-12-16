package com.example.learningtolive;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

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

    public void getValue(String reference, MyCallback myCallback) {
        DatabaseReference ref = db.getReference(reference);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> data = new HashMap<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d(TAG, ds.getKey() + "  " + ds.getValue());
                    data.put(ds.getKey(), ds.getValue().toString());
                }
                myCallback.onCallBack(reference, data, !data.isEmpty());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}
