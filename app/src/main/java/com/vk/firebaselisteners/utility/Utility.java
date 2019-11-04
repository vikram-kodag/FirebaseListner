package com.vk.firebaselisteners.utility;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class Utility {

    public static boolean isEmptyOrNull(String value) {
        return (value == null || value.isEmpty());
    }

    public static void showMessage(Context activity, String message) {
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        toast.show();
    }
}
