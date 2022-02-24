package com.example.gpsexample;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener, OnMapReadyCallback {

    LocationManager locationManager;
    LocationProvider locationProvider;
    GoogleMap myMap;
    TextView tvData;
    Button btGetLocation;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        requestPermission(Manifest.permission.INTERNET);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        mapView = findViewById(R.id.map);
        tvData = findViewById(R.id.tvData);
        btGetLocation = findViewById(R.id.btGetLocation);

        btGetLocation.setOnClickListener(this);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lng));
        markerOptions.title("MyMarker");
        myMap.addMarker(markerOptions);
        tvData.setText(String.format("Longitude: %f\nLatitude: %f", lng, lat));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view == btGetLocation) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        }
    }

    public boolean permissionGranted(String permission)
    {
        // Check if the app is api 23 or above
        // If so, must ask for permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                Log.d("Permissions", "permission granted");
                return true;
            }
            Log.d("Permissions", "Permission was not granted");
            return false;
        }
        // Permission is automatically granted on sdk<23 upon installation
        Log.d("Permissions", "permission granted");
        return true;
    }

    public boolean requestPermission(String permission)
    {
        if (permissionGranted(permission))
            return true;
        // Ask for permission
        ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        return false;
    }

    public void requestLocationPermission() {
        // Check if the app is api 23 or above
        // If so, must ask for permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            String coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION;
            String finePermission = Manifest.permission.ACCESS_FINE_LOCATION;
            String[] permissions = new String[]{coarsePermission, finePermission};
            requestPermissions(permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION;
        String finePermission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (checkSelfPermission(finePermission) !=
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(coarsePermission) !=
                            PackageManager.PERMISSION_GRANTED)
            {
                Log.d("Permissions", "Permissions was not granted");
                return;
            }
            Log.d("Permissions", "Permissions was granted");
            // Register to get location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000, // 10-second interval.
                    10, // 10 meters.
                    this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}