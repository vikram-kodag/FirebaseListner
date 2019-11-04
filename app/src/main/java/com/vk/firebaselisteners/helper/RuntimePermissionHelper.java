package com.vk.firebaselisteners.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseIntArray;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public abstract class RuntimePermissionHelper extends Fragment {//AppCompatActivity {
    private SparseIntArray mErrorString;
    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
    }

    public void requestAppPermissions(final Activity activity, final String[] requestedPermissions,
                                      final int stringId, final int requestCode) {
        if (activity != null && mErrorString != null) {
            this.activity = activity;
            mErrorString.put(requestCode, stringId);
            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            boolean shouldShowRequestPermissionRationale = false;
            for (String permission : requestedPermissions) {
                permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(getActivity(), permission);
                shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        }
    }

    public abstract void onPermissionsGranted(int requestCode);

    public boolean checkPermission(String permission, Context context) {
        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(permission, context.getPackageName());
        return (hasPerm == PackageManager.PERMISSION_GRANTED);
    }

    public boolean checkPermissionGranted(String permission, Context context) {
        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(permission, context.getPackageName());
        return (hasPerm == PackageManager.PERMISSION_GRANTED);
    }
}