package com.vk.firebaselisteners.utility;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class Utility {

    public static boolean isEmptyOrNull(String value) {
        return (value == null || value.isEmpty());
    }

    public static void showMessage(Context activity, String message) {
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        View view = toast.getView();
        toast.show();
    }

    public static String getNewId() {
        Random random = new Random();
        return ("Emp-" + String.format("%04d", random.nextInt(10000)));
    }
}
