package com.cs407.badgerbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationCallback locationCallback;
    private Location currentLocation;
    Marker marker;
    SearchView searchView;
    FloatingActionButton floatingActionButtonMyLocation;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //start app with splash screen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect with map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//
//                    LatLng userLocation = new LatLng(latitude, longitude);
//                    mMap.addMarker(new MarkerOptions().position(userLocation).title("You are here"));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15)); // Adjust the zoom level as needed
                }
            }
        };

        // make sure permission and then request access
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
//            startLocationUpdates();
            getCurrentLocation();
        }

        // get current location when pressing the location button - what is this for? (Jade)
        /**floatingActionButtonMyLocation = findViewById(R.id.btn_my_location);
        floatingActionButtonMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });**/



        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        // input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String strLocation = searchView.getQuery().toString();

                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(strLocation, 1);
                    if (addressList.size() > 0) {
                        LatLng latLng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
                        if (marker != null) {
                            marker.remove();
                        }
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(strLocation);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                        mMap.animateCamera(cameraUpdate);
                        marker = mMap.addMarker(markerOptions);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    //go to ListActivity when clicking button
    public void clickListButton(View view){
        startActivity(new Intent(HomeActivity.this, ListActivity.class));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(43.07630232892188, -89.40010886244517));
        markerOptions.title("Memorial Union");
        mMap.addMarker(markerOptions);

        mMap.setOnMarkerClickListener(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startLocationUpdates();
                getCurrentLocation();
            } else {
                // if user decides not to share location on start of the app
                Toast.makeText(this, "Location permission denied. Cannot get your location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 지속적으로 내 위치 업데이트 하기 위한 함수 : 앱 리소스 낭비가 있음.
    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(100)
                .build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //fusedLocationProviderClient = map interface
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return this;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }

        }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                LatLng userLocation = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(userLocation).title("You are here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15)); // Adjust the zoom level as needed
            }
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        LatLng latLng = marker.getPosition();
        String title = marker.getTitle();
        String address = "800 Langdon St, Madison, WI 53703 USA";
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address tempAddress = addresses.get(0);
                address = tempAddress.getAddressLine(0);
            }
        } catch (IOException e) {
            Log.e("ReverseGeocoding", "Error retrieving address from location", e);
        }

        MapInfoBottomSheetDialog bottomSheetDialog = MapInfoBottomSheetDialog.newInstance(title, address);
        bottomSheetDialog.show(getSupportFragmentManager(), bottomSheetDialog.getTag());

        return false;
    }
}