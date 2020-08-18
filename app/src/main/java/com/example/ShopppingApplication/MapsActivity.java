package com.example.ShopppingApplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    EditText addressText;
    LocationManager locationManager;
    MyLocationListener locListener;
    Button locationBtn;
    ArrayList<String> iDArray;
    ArrayList<String> quantityArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        iDArray = new ArrayList<String>();
        iDArray = getIntent().getStringArrayListExtra("productsID");
        quantityArray = new ArrayList<String>();
        quantityArray = getIntent().getStringArrayListExtra("productsQuantity");

        addressText = (EditText)findViewById(R.id.loc_text);
        locationBtn = (Button)findViewById(R.id.loc_Btn);

        locListener = new MyLocationListener(getApplicationContext());
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        try
        {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER , 6000,0,locListener);
        }
        catch (SecurityException ex)
        {
            Toast.makeText(this, "you are not allowed to access the current location", Toast.LENGTH_LONG).show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960 , 31.235711600),8));

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc= null;
                try
                {
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(), "you are not allowed to access the current location", Toast.LENGTH_SHORT).show();
                }
                if (loc != null)
                {
                    LatLng myPostision = new LatLng(loc.getLatitude(), loc.getLongitude() );
                    try
                    {
                        addressList = coder.getFromLocation(myPostision.latitude,myPostision.longitude,1);
                        if (!addressList.isEmpty())
                        {
                            String address = "";
                            for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++)
                                address += addressList.get(0).getAddressLine(i) + ", ";
                            mMap.addMarker(new MarkerOptions().position(myPostision).title("My location").snippet(address)).setDraggable(true);
                            addressText.setText(address);

                        }
                    }
                    catch (IOException e)
                    {
                        mMap.addMarker(new MarkerOptions().position(myPostision).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPostision , 15));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please wait untill your position is determined", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {



            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try
                {
                    addressList = coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if (!addressList.isEmpty())
                    {
                        String address = "";
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++)
                            address += addressList.get(0).getAddressLine(i) + ", ";
                        addressText.setText(address);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No address for this location", Toast.LENGTH_SHORT).show();
                        addressText.getText().clear();
                    }
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(), "Check your network", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Done_location_clicked(View view)
    {
        if (addressText.getText().toString().equals(""))
        {
            Toast.makeText(this, "Please enter you address", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(MapsActivity.this, OrderDetailsActivity.class);
            i.putExtra("productsID", iDArray);
            i.putExtra("productsQuantity", quantityArray);
            i.putExtra("address", addressText.getText().toString());
            startActivity(i);
        }
    }
}
