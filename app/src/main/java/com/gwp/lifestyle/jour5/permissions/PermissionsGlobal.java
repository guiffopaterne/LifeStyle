package com.gwp.lifestyle.jour5.permissions;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class PermissionsGlobal {
    public static ArrayList<String> permissionsNeed =  new ArrayList<>();
    public static int REQUEST_CODE_PERMISSION = 100;

    public static void getRequiredPermissions(Context ctx){
        permissionsNeed.clear();
        if(ContextCompat.checkSelfPermission(ctx,INTERNET)!= PackageManager.PERMISSION_GRANTED){
            permissionsNeed.add(INTERNET);
        }
        if(ContextCompat.checkSelfPermission(ctx,ACCESS_NETWORK_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionsNeed.add(ACCESS_NETWORK_STATE);
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(ctx,POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                permissionsNeed.add(POST_NOTIFICATIONS);
            }
        }
        Log.e("PERMISSION","lES PERMISSIONS No GRANTED = "+ permissionsNeed.toString());
    }

    public static void requestPermissions(Activity activity){
        getRequiredPermissions(activity);
        if(!permissionsNeed.isEmpty()){
            ActivityCompat.requestPermissions(activity,permissionsNeed.toArray(new String[0]),REQUEST_CODE_PERMISSION);
        }
    }
}
