package com.vk.firebaselisteners.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseIntArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class PermissionHelperActivity extends AppCompatActivity {
    private SparseIntArray mErrorString;
    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
    }

    public void requestAppPermissions(final Activity activity, final String[] requestedPermissions,
                                      final int stringId, final int requestCode) {
        this.activity = activity;
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(activity, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                ActivityCompat.requestPermissions(activity, requestedPermissions, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, requestedPermissions, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(int requestCode);

    public boolean checkPermission(String permission) {
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}