package com.gwp.lifestyle.jour4.bottom_navigation.ui.maps;

import static com.gwp.lifestyle.jour5.permissions.PermissionsDetails.checkAndRequestAccessPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.gwp.lifestyle.LifeStyleApplication;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.FragmentMapsBinding;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class MapsFragment extends Fragment {
    MapView mapView;
    IMapController mapController;
    Activity activity;

    private FragmentMapsBinding binding;
    private Handler handler = new Handler(Looper.getMainLooper());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        Context ctx = LifeStyleApplication.instance.getApplicationContext();
        Configuration.getInstance().load(ctx,LifeStyleApplication.instance.getPrefs());
        Configuration.getInstance().setUserAgentValue("LifeStyleApplication/1.0 (guiffopaterne@gmail.com)");

        mapView = binding.mapView;
        mapView.setMultiTouchControls(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapController = mapView.getController();
        mapController.setZoom(14);
//        mapController.setCenter(new GeoPoint(48.745, -3.455));
        ActivityResultLauncher<String> permissionAccess = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if(isGranted){
                        getLocation();
                        return;
                    }
                    Toast.makeText(activity, "Permission Non Accordee", Toast.LENGTH_SHORT).show();

                }
        );
        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }else{
            checkAndRequestAccessPermission(activity,permissionAccess);
        }


    }

    private void getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        handler.post(()->{
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Log.e("LOCALISATION",latitude+" "+ longitude);
                            makePosition(latitude,longitude);
                            } else {
                            Toast.makeText(activity, "Impossible d'obtenir la localisation", Toast.LENGTH_SHORT).show();
                            Log.e("LOCALISATION","Rien n'a fonctionne");
                        }
                    });
        });
    }

    private void makePosition(double latitude, double longitude){
        Marker marker = new Marker(mapView);
        GeoPoint userLocation = new GeoPoint(latitude,longitude);
        mapController.setCenter(userLocation);
        marker.setPosition(userLocation);
        marker.setTitle("Votre position");
        marker.setIcon(activity.getDrawable( org.osmdroid.library.R.drawable.osm_ic_follow_me_on));
        mapView.getOverlays().add(marker);
        mapView.invalidate();
        Toast.makeText(activity, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}