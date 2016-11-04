package io.pello.android.locationsample;

import android.Manifest;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class GPSActivity extends AppCompatActivity {

    private TextView longitudeGPSTextView;
    private TextView latitudeGPSTextView;
    private LocationManager locationManager;
    private String provider;
    private Location location;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);


        longitudeGPSTextView = (TextView) findViewById(R.id.longitudeGPSTextView);
        latitudeGPSTextView = (TextView) findViewById(R.id.latitudeGPSTextView);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



    }


    public void getPosition(View view) {

        Log.d("PELLODEBUG", "Get GPS Position: ");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);//true if required
        criteria.setBearingRequired(false);//true if required
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        provider = locationManager.getBestProvider(criteria, false);//search for enabled provider
        Log.d("PELLODEBUG", "Provider found: " + provider);
    try {
        location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            float lat = (float) (location.getLatitude());
            float lng = (float) (location.getLongitude());
            latitudeGPSTextView.setText(String.valueOf(lat));
            longitudeGPSTextView.setText(String.valueOf(lng));
        } else {
            latitudeGPSTextView.setText("Provider not available");
            longitudeGPSTextView.setText("Provider not available");
        }


       locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    } catch (SecurityException se) {
        Log.d("PELLODEBUG","Security Exception trying to get Location " + se.getMessage());
    }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        // locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        //locationManager.removeUpdates(this);
    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            Log.d("PELLODEBUG",location.toString());
            float lat = (float) (location.getLatitude());
            float lng = (float) (location.getLongitude());
            latitudeGPSTextView.setText(String.valueOf(lat));
            longitudeGPSTextView.setText(String.valueOf(lng));
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {
            //Toast.makeText(this, "Enabled new provider " + provider,Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
            //Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
        }
    };




}
