package com.gwp.lifestyle.jour5.permissions;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

public class PermissionsDetails {
    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void checkAndRequestPermission(Activity activity, String permission, ActivityResultLauncher<String> launcher) {
        if (!isPermissionGranted(activity, permission)) {
            launcher.launch(permission);
        }
    }
    public static void checkAndRequestNotificationPermission(Activity activity, ActivityResultLauncher<String> launcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestPermission(activity, POST_NOTIFICATIONS, launcher);
        }
    }



}
