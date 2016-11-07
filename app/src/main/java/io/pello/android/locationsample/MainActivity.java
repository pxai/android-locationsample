package io.pello.android.locationsample;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

//import gms.drive.*;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Gets coordinates using google play API.
 * Check the listeners
 * @author PELLO_ALTADILL
 *
 */
public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient = null;
    private Location lastLocation = null;
    private TextView longitudeTextView;
    private TextView latitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        longitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        latitudeTextView = (TextView) findViewById(R.id.latitudeTextView);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("PELLODEBUG","Ok, added Api Client");
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        String location = "";

        if (lastLocation != null) {
            location = "Lat: " + String.valueOf(lastLocation.getLatitude());
            location += "Long: " +  String.valueOf(lastLocation.getLongitude());

            longitudeTextView.setText(String.valueOf(lastLocation.getLongitude()));
            latitudeTextView.setText(String.valueOf(lastLocation.getLatitude()));

            Log.d("PELLODEBUG", location);
        }
        Log.d("PELLODEBUG", "Finished onConnected");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("PELLODEBUG", "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("PELLODEBUG", "Connection failed");
    }

    public void getPosition (View view) {
        Log.d("PELLODEBUG", "Get Position: ");
    }

    public void openGPSActivity (View view) {
        Log.d("PELLODEBUG", "Open GPS Position Activity ");
        Intent gpsActivityIntent = new Intent(this, GPSActivity.class);
        this.startActivity(gpsActivityIntent);

    }
}
