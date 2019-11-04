package com.vk.firebaselisteners.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vk.firebaselisteners.callback.CallBack;
import com.vk.firebaselisteners.callback.FirebaseChildCallBack;
import com.vk.firebaselisteners.constants.Constant;
import com.vk.firebaselisteners.exception.ExceptionUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class FirebaseRepository {

    /**
     * Insert data on FireBase
     *
     * @param databaseReference Database reference of data to be add
     * @param model Model to insert into database
     * @param callback callback for event handling
     */
    protected final void fireBaseCreate(final DatabaseReference databaseReference, final Object model, final CallBack callback) {
        databaseReference.keepSynced(true);
        databaseReference.setValue(model, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess(Constant.SUCCESS);
                } else {
                    callback.onError(databaseError);
                }
            }
        });
    }

    /**
     * Update data to FireBase
     *
     * @param databaseReference  Database reference of data to update
     * @param map Data map to update
     * @param callback callback for event handling
     */
    protected final void fireBaseUpdateChildren(final DatabaseReference databaseReference, final Map map, final CallBack callback) {
        databaseReference.keepSynced(true);
        databaseReference.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,@NonNull  DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess(databaseError);
                } else {
                    callback.onError(databaseError);
                }
            }
        });
    }

    /**
     * Delete data from firebase
     *
     * @param databaseReference  Database reference of data to delete
     * @param callback callback for event handling
     */
    protected final void fireBaseDelete(final DatabaseReference databaseReference, final CallBack callback) {
        databaseReference.keepSynced(true);
        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,@NonNull  DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(databaseError);
                }
            }
        });
    }


    /**
     * Getting data from FireBase only single time
     *
     * @param query  query of database reference to fetch data
     * @param callback  callback for event handling
     */
    protected final void fireBaseReadData(final Query query, final CallBack callback) {
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

    /**
     * Fetch data with child event listener
     *
     * @param query    to add childEvent listener
     * @param firebaseChildCallBack  callback for event handling
     * @return ChildEventListener
     */
    protected final ChildEventListener fireBaseChildEventListener(final Query query, final FirebaseChildCallBack firebaseChildCallBack) {
        query.keepSynced(true);
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                firebaseChildCallBack.onChildAdded(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                firebaseChildCallBack.onChildChanged(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                firebaseChildCallBack.onChildRemoved(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                firebaseChildCallBack.onChildMoved(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseChildCallBack.onCancelled(databaseError);
            }
        };
    }

    /**
     * Fetch data with Value event listener
     *
     * @param query    to add childEvent listener
     * @param callback  callback for event handling
     * @return ValueEventListener reference
     */
    protected final ValueEventListener fireBaseDataChangeListener(final Query query, final CallBack callback) {
        query.keepSynced(true);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        };
        query.addValueEventListener(valueEventListener);
        return valueEventListener;
    }

    /**
     * Insert offline data on FireBase
     *
     * @param databaseReference  Database reference of data to create
     * @param model Model to insert into database
     */
    protected final void fireBaseOfflineCreate(final DatabaseReference databaseReference, final Object model) {
        try {
            databaseReference.keepSynced(true);
            databaseReference.setValue(model);
        } catch (Exception e) {
            ExceptionUtil.errorMessage("Method: fireBaseOfflineCreateAndUpdate", "Class: FirebaseTemplateRepository", e);
        }
    }

    /**
     * update offline data on FireBase
     *
     * @param databaseReference  Database reference of data to update
     * @param model Model to update into database
     */
    protected final void fireBaseOfflineUpdate(final DatabaseReference databaseReference, final String pushKey, final Object model) {
        try {
            databaseReference.keepSynced(true);
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put(pushKey, model);
            databaseReference.updateChildren(stringObjectMap);

        } catch (Exception e) {
            ExceptionUtil.errorMessage("Method: fireBaseOfflineCreateAndUpdate", "Class: FirebaseTemplateRepository", e);
        }
    }

}