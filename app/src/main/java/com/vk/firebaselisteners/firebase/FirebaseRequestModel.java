package com.vk.firebaselisteners.firebase;

import com.google.firebase.database.Query;

public class FirebaseRequestModel {
    private Object listner;
    private Query query;

    public FirebaseRequestModel(Object listner, Query query) {
        this.listner = listner;
        this.query = query;
    }

    public Object getListner() {
        return listner;
    }

    public void setListner(Object listner) {
        this.listner = listner;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}