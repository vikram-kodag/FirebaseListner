package com.vk.firebaselisteners.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class PermissionHelper {
    /**
     * Two possible callback: granted or not.
     */
    public interface PermissionListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }

    private static final int REQUEST_CODE = 1337;

    // Keep instance of last listener & message fail.
    private static PermissionListener last_listener;
    private static @StringRes
    int last_messsage_fail;

    public static class Builder {
        private Activity activity;
        private String permission;
        private PermissionListener listener;
        private @StringRes
        int message_explain;
        private @StringRes
        int messsage_fail;

        private Builder() {

        }

        /**
         * Manage permissions.
         *
         * @param activity        Calling activity.
         * @param permission      Permission to ask.
         * @param listener        Callback..
         * @param message_explain Message to show before to ask permission.
         * @param messsage_fail   Message to show if user refused to grant permission.
         */
        public static Builder goWithPermission(@NonNull final Activity activity,
                                               @NonNull final String permission,
                                               @Nullable final PermissionListener listener,
                                               @StringRes final int message_explain,
                                               @StringRes final int messsage_fail) {
            Builder builder = new Builder();
            builder.activity = activity;
            builder.permission = permission;
            builder.listener = listener;
            builder.message_explain = message_explain;
            builder.messsage_fail = messsage_fail;
            return builder;
        }

        public PermissionHelper start() {
            return new PermissionHelper(this);
        }
    }

    private PermissionHelper(final Builder builder) {
        last_listener = builder.listener;
        last_messsage_fail = builder.messsage_fail;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int perm = builder.activity.checkSelfPermission(builder.permission);
            if (perm != PackageManager.PERMISSION_GRANTED) {

                // If user do not want to be bother again (cliked on "Never ask again").
                if (!builder.activity.shouldShowRequestPermissionRationale(builder.permission)) {
                    showFailMessage(builder.activity);
                    return;
                }

                // Inform user why we ask permission
                final AlertDialog.Builder dialog = new AlertDialog.Builder(builder.activity);
                dialog.setTitle("Permission required");
                dialog.setMessage(builder.message_explain);
                dialog.setPositiveButton(android.R.string.ok, null);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            builder.activity.requestPermissions(new String[]{builder.permission}, REQUEST_CODE);
                        }
                    }
                });

                dialog.show();

                } else if (last_listener != null) {
                    last_listener.onPermissionGranted();
                }
            } else if (last_listener != null) {
                last_listener.onPermissionGranted();
            }
        }

        /**
         * Must be called from Activity's onRequestPermissionsResult()
         */
    public static void onRequestPermissionsResult(@NonNull Activity activity,
                                                  int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (last_listener != null) {
                        last_listener.onPermissionGranted();
                    }
                } else if (last_messsage_fail != 0) {
                    showFailMessage(activity);
                } else if (last_listener != null) {
                    last_listener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * Show final fail message.
     */
    private static void showFailMessage(@NonNull Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
       // builder.setTitle(R.string.AndroidPermission_storageLimited_title3);
        builder.setMessage(last_messsage_fail);
        builder.setPositiveButton(android.R.string.ok, null);

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (last_listener != null) {
                    last_listener.onPermissionDenied();
                }
            }
        });
        builder.show();
    }

}