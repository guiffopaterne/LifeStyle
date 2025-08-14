package com.gwp.lifestyle.jour4.bottom_navigation;

import static com.gwp.lifestyle.jour5.permissions.PermissionsDetails.checkAndRequestNotificationPermission;
import static com.gwp.lifestyle.jour5.permissions.PermissionsGlobal.REQUEST_CODE_PERMISSION;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.ActivityBottomNavigationBinding;
import com.gwp.lifestyle.jour5.InternetCheckService;
import com.gwp.lifestyle.jour5.permissions.PermissionsGlobal;

public class BottomNavigationActivity extends AppCompatActivity {

    private ActivityBottomNavigationBinding binding;
    private MaterialToolbar toolbar;
    private ActivityResultLauncher<String> permissionNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        BottomNavigationView navView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_camera, R.id.navigation_list, R.id.navigation_maps)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//        PermissionsGlobal.requestPermissions(this);
//        Request Permission pour un singleton
        permissionNotification = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted-> Toast.makeText(this,"Permission "+ (isGranted?"":"non " )+"Accordee",Toast.LENGTH_SHORT).show()
        );
        checkAndRequestNotificationPermission(this,permissionNotification);

//        request permissions pour permission multiple
        PermissionsGlobal.requestPermissions(this);
//
//        startInternetCheckService();
    }

    private void startInternetCheckService() {
        Intent serviceIntent = new Intent(this, InternetCheckService.class);
        startForegroundService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopInternetCheckService();
    }

    private void stopInternetCheckService() {
        Intent serviceIntent = new Intent(this, InternetCheckService.class);
        stopService(serviceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_PERMISSION){
            Toast.makeText(this,"Permission "+ (grantResults.length==0?"":"non " )+"Accordee",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}