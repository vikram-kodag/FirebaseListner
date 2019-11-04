package com.vk.firebaselisteners.firebase;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtility {
    public static void removeFireBaseChildListener( Activity activity,FirebaseRequestModel firebaseRequestModel) {
        if (firebaseRequestModel != null) {
            if (firebaseRequestModel.getListner() != null && firebaseRequestModel.getQuery() != null) {
                ChildEventListener childEventListener = (ChildEventListener) firebaseRequestModel.getListner();
                firebaseRequestModel.getQuery().removeEventListener(childEventListener);
                Glide.get(activity).clearMemory();//clear memory
            }
        }
    }

    public static void removeFireBaseValueEventListener( Activity activity,FirebaseRequestModel firebaseRequestModel) {
        if (firebaseRequestModel != null) {
            if (firebaseRequestModel.getListner() != null && firebaseRequestModel.getQuery() != null) {
                ValueEventListener valueEventListener = (ValueEventListener) firebaseRequestModel.getListner();
                firebaseRequestModel.getQuery().removeEventListener(valueEventListener);
                Glide.get(activity).clearMemory();//clear memory
            }
        }
    }

}
