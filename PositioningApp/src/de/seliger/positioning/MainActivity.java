package de.seliger.positioning;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button btnGetPosition;
    private ProgressBar progressBar2;
    private PositionLocator locator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locator = new PositionLocator(MainActivity.this);

        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        progressBar2.clearAnimation();
        progressBar2.setVisibility(View.INVISIBLE);

        btnGetPosition = (Button)findViewById(R.id.btnGetPosition);
        btnGetPosition.setOnClickListener( new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                progressBar2.setVisibility(View.VISIBLE);
                //                progressBar.animate();
                //                progressBar.callOnClick();
                //                progressBar.bringToFront();

                //                progressBar2.animate();
                //                progressBar2.callOnClick();

                try {
                    String message = "unbekannte Position";
                    if (locator.canGetLocation()) {
                        double latitude = locator.getLatitude();
                        double longitude = locator.getLongitude();

                        message = "Ihre Position ist - Breite: " + latitude + ", Länge: " + longitude;
                        // \n is for new line
                        //                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }
                    TextView lblPosition = (TextView)findViewById(R.id.lblPosition);
                    lblPosition.setText(message);
                } finally {
                    progressBar2.clearAnimation();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
