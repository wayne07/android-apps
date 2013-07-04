package de.seliger.speedobike;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.seliger.speedobike.location.LocationTracker;

public class MainActivity extends Activity {

    private TextView lblSpeed;
    private TextView lblPosition;
    private ProgressBar progressBarTrackRunning;

    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationTracker = new LocationTracker(this);

        lblPosition = (TextView)findViewById(R.id.textViewPosition);
        lblSpeed = (TextView)findViewById(R.id.lblSpeed);
        progressBarTrackRunning = (ProgressBar)findViewById(R.id.progressBarTrackRunning);
        progressBarTrackRunning.clearAnimation();
        progressBarTrackRunning.setVisibility(View.INVISIBLE);
        addButtonListeners();
    }

    private void addButtonListeners() {
        findViewById(R.id.buttonStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("tracking", "start tracking");
                locationTracker.startTracking();
                progressBarTrackRunning.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.buttonStop).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("tracking", "stop tracking");
                locationTracker.stopUsingGPS();
                progressBarTrackRunning.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void setInfoFromLocation(Location location) {
        if (location == null) {
            resetAllFields();
        }
        setLocationText(location);
        lblSpeed.setText(location.getSpeed() + " m/sec");
    }

    private void resetAllFields() {
        lblPosition.setText("Position nicht bestimmt");
        lblSpeed.setText("");
    }

    private void setLocationText(Location location) {
        String message = "unbekannte Position";
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        message = "Ihre Position ist - Breite: " + latitude + ", Länge: " + longitude;
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
