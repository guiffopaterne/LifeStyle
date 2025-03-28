package com.gwp.lifestyle.jour5.permissions;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
//        Permissions independant des version d'api a partir de 26
        String[] permissions  = {INTERNET,ACCESS_NETWORK_STATE,RECEIVE_BOOT_COMPLETED, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(ctx,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionsNeed.add(permission);
            }
        }
//        Contrainte sur les version d'api
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(ctx,POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                permissionsNeed.add(POST_NOTIFICATIONS);
            }
        }

        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(ctx, ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeed.add(ACCESS_BACKGROUND_LOCATION);
            }
        }


        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            if(ContextCompat.checkSelfPermission(ctx,WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                permissionsNeed.add(WRITE_EXTERNAL_STORAGE);
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
