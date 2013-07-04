package de.seliger.speedobike.location;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import de.seliger.speedobike.MainActivity;

public class LocationTracker extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 second

    private final MainActivity mainActivity;

    // flag for GPS status
    private boolean isGPSEnabled = false;
    // flag for network status
    private boolean isNetworkEnabled = false;
    // flag for GPS status
    private boolean canGetLocation = false;
    // private Location location; // location
    private double latitude; // latitude
    private double longitude; // longitude
    // Declaring a Location Manager
    private final LocationManager locationManager;

    public LocationTracker(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        locationManager = (LocationManager)mainActivity.getSystemService(LOCATION_SERVICE);
    }

    public void startTracking() {
        requestLocationUpdates();
    }

    public boolean canGetLocation() {
        return canGetLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getPositionFromLocation(location);
        mainActivity.setLocationText();
    }

    private void getPositionFromLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    private void requestLocationUpdates() {
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if ( !isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
            showSettingsAlert();
        } else {
            this.canGetLocation = true;
            if (isNetworkEnabled) {
                Log.d("Network", "Network is enabled, requesting Location-Updates ...");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
            if (isGPSEnabled) {
                Log.d("GPS", "GPS is enabled, requesting Location-Updates ...");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
        }

    }

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mainActivity.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // wird nicht benötigt
    }

    @Override
    public void onProviderEnabled(String provider) {
        // wird nicht benötigt
    }

    @Override
    public void onProviderDisabled(String provider) {
        // wird nicht benötigt
    }

    @Override
    public IBinder onBind(Intent intent) {
        // wird nicht benötigt
        return null;
    }

}
