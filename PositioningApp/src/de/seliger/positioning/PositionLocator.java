package de.seliger.positioning;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;


public class PositionLocator extends Service implements LocationListener {

    private final Context appContext;

    // flag for GPS status
    private boolean isGPSEnabled = false;

    // flag for network status
    private boolean isNetworkEnabled = false;

    // flag for GPS status
    private boolean canGetLocation = false;

    //    private Location location; // location
    private double latitude; // latitude
    private double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public PositionLocator(Context context) {
        this.appContext = context;
        getLocationFromProvider();
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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void getLocationFromProvider() {
        try {
            locationManager = (LocationManager)appContext.getSystemService(LOCATION_SERVICE);

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
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    //                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        getPositionFromLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    //                        Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        getPositionFromLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPositionFromLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(appContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                appContext.startActivity(intent);
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


    /*
        public void onLocationChanged(Location loc) {            
            editLocation.setText("");  
            pb.setVisibility(View.INVISIBLE);  
              Toast.makeText(getBaseContext(),       
                "Location changed: Lat: " +loc.getLatitude()+" Lng: "  
                 + loc.getLongitude(), Toast.LENGTH_SHORT).show();  
              String longitude = "Longitude: " + loc.getLongitude();  
              Log.v(TAG, longitude);  
               String latitude = "Latitude: " + loc.getLatitude();  
              Log.v(TAG, latitude);  

    //           ------to get City-Name from coordinates --------   
               String cityName = null;  
               Geocoder gcd = new Geocoder(getBaseContext(),   
                 Locale.getDefault());  

               List<Address> addresses;  
               try {  
                addresses = gcd.getFromLocation(loc.getLatitude(),  
                  loc.getLongitude(), 1);  
                if (addresses.size() > 0)  
                 System.out.println(addresses.get(0).getLocality());  
                cityName = addresses.get(0).getLocality();  
               } catch (IOException e) {  
                e.printStackTrace();  
               }  

               String s = longitude + "\n" + latitude  
                 + "\n\nMy Current City is: " + cityName;  
               editLocation.setText(s);  
              }  
    */

    private Location getLastBestLocation() {
        //        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //
        //        long GPSLocationTime = 0;
        //        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }
        //
        //        long NetLocationTime = 0;
        //
        //        if (null != locationNet) {
        //            NetLocationTime = locationNet.getTime();
        //        }
        //
        //        if ( 0 < GPSLocationTime - NetLocationTime ) {
        //            return locationGPS;
        //        }
        //        else{
        //            return locationNet;
        //        }
        return null;
    }
}
