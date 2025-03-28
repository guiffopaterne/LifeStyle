package com.gwp.lifestyle.jour5;

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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionsGlobal {
    public static final int REQUEST_PERMISSIONS_CODE = 100;
    public static List<String> getRequiredPermissions(Context context) {
        List<String> permissionsNeeded = new ArrayList<>();

        // Vérification des permissions réseau

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
//        POST_NOTIFICATIONS
        String[] audios_permission = {RECORD_AUDIO, READ_SMS, RECEIVE_SMS, SEND_SMS};

        for (String permission : audios_permission) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        // Permissions de stockage selon la version Android
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) { // Android 12L et avant
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        } else { // Android 13+
            if (ContextCompat.checkSelfPermission(context, POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(POST_NOTIFICATIONS);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_VIDEO)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_VIDEO);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.READ_MEDIA_AUDIO);
            }
        }

        // Permissions spécifiques à Android 14+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34 (Android 14)
            if (ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(READ_MEDIA_VISUAL_USER_SELECTED);
            }
        }

        return permissionsNeeded;
    }

    /**
     * Vérifie si l'application a toutes les permissions requises.
     *
     * @param context Contexte de l'application
     * @return true si toutes les permissions sont accordées, false sinon
     */
    public static boolean hasMediaPermissions(Context context) {
        return getRequiredPermissions(context).isEmpty();
    }

    /**
     * Demande les permissions manquantes.
     *
     * @param activity L'activité en cours
     */
    public static void requestPermissions(Activity activity) {
        List<String> permissions = getRequiredPermissions(activity);
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[0]), REQUEST_PERMISSIONS_CODE);
        }
    }

    public static Boolean permissionOk(Activity activity) {
        List<String> permissions = getRequiredPermissions(activity);
        return permissions.isEmpty();
    }

}
