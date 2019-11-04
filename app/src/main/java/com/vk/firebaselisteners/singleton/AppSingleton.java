package com.vk.firebaselisteners.singleton;

import android.content.Context;


public class AppSingleton {
    private static AppSingleton appSingleton;
    private static Context context;

    //private constructor.
    private AppSingleton(Context context) {
    }

    public static AppSingleton getInstance(Context appContext) {
        //if there is no instance available... create new one
        context = appContext;
        if (appSingleton == null) {
            appSingleton = new AppSingleton(appContext);
        }
        return appSingleton;
    }
}