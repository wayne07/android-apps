package de.seliger.speedobike;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import de.seliger.speedobike.location.LocationTracker;

public class MainActivity extends Activity {

    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTracker = new LocationTracker(this);
        locationTracker.startTracking();
    }

    public void setLocationText() {
        String message = "unbekannte Position";
        if (locationTracker.canGetLocation()) {
            double latitude = locationTracker.getLatitude();
            double longitude = locationTracker.getLongitude();

            message = "Ihre Position ist - Breite: " + latitude + ", Länge: " + longitude;
            // \n is for new line
            //                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
        TextView lblPosition = (TextView)findViewById(R.id.textViewPosition);
        lblPosition.setText(message);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public boolean isFinishing() {
        locationTracker.stopUsingGPS();
        return super.isFinishing();
    }

}
