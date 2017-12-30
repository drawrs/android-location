package com.example.root.devicecurrentlocation;

import android.Manifest;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.utils.LocationState;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final int RC_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Priodic location
//        SmartLocation.with(this).location().start(new OnLocationUpdatedListener() {
//            @Override
//            public void onLocationUpdated(Location location) {
//                Log.d("Locations lat", ""+ location.getLatitude());
//                Log.d("Locations long", ""+ location.getLongitude());
//            }
//        });
        requestLocationPermission();

        // Get loocation
        getUserLocation();

    }

    private void getUserLocation() {
        LocationState locationState = SmartLocation.with(this).location().state();
        // Check if the location services are enabled
        if(locationState.locationServicesEnabled() && locationState.isGpsAvailable()){
            // One Fix location
            SmartLocation.with(this).location().oneFix().config(LocationParams.BEST_EFFORT).start(new OnLocationUpdatedListener() {
                @Override
                public void onLocationUpdated(Location location) {
                    Log.d("Locations lat", ""+ location.getLatitude());
                    Log.d("Locations long", ""+ location.getLongitude());

                    Toast.makeText(MainActivity.this, "Lat : " + location.getLatitude() + " Long : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Minta askses GPS
            Toast.makeText(this, "Location Service & GPS are disabled. Please enable !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

        // Get user location
        getUserLocation();
    }
    private void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.LOCATION_HARDWARE, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            getUserLocation();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Membutuhkan akses GPS",
                    RC_LOCATION, perms);

        }
    }

}
