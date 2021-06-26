package com.example.lemonlocation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // manager.getLastKnownlocatiob(0)
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = manager.getBestProvider(criteria, true);

        textView.setText("kk");
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                makeUseOfNewLocation(location);
            }
        };

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            else{
                textView.setText("Please move device up and down to see location.");
            }
            manager.requestLocationUpdates(provider, 0, 0, listener);
        }
        catch (Exception e){
            textView.setText("onCreate: Error: " + e.getMessage());
            Log.d("TAG99", "onCreate: Error: " + e.getMessage());
        }
    }

    private void makeUseOfNewLocation(Location location){
        textView.setText("Longiude: " + location.getLongitude() + "\nLatitude: " + location.getLatitude() + "\nAltitude: " + location.getAltitude());
    }

    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher, as an instance variable.
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });
}