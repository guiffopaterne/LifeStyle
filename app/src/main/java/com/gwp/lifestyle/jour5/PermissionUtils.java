package com.gwp.lifestyle.jour5;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {


    private ActivityResultLauncher<String> permissionLauncher;
//


    /**
     * Vérifie si une permission spécifique est accordée.
     */
    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Vérifie et demande une permission spécifique.
     */
    public static void checkAndRequestPermission(Activity activity, String permission, ActivityResultLauncher<String> launcher) {
        if (!isPermissionGranted(activity, permission)) {
            launcher.launch(permission);
        }
    }

    /**
     * Vérifie et demande la permission des notifications (Android 13+).
     */
    public static void checkAndRequestNotificationPermission(Activity activity, ActivityResultLauncher<String> launcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestPermission(activity, POST_NOTIFICATIONS, launcher);
        }
    }



}

