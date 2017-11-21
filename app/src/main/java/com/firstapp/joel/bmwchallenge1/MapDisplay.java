package com.firstapp.joel.bmwchallenge1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by joel on 11/21/2017.
 */
public class MapDisplay extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    LatLng Current = null;
    TextView addr,nameTxt,la,lo,arive;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaymaps);
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapDisplay.this,MainActivity.class);
                startActivity(i);
            }
        });

        //Views
        addr = findViewById(R.id.address);
        nameTxt = findViewById(R.id.name);
        la = findViewById(R.id.lati);
        lo = findViewById(R.id.longi);
        arive = findViewById(R.id.arrival);

        Intent intent = getIntent();
        Double lat = intent.getDoubleExtra("latitude",0.0);
        Double lon = intent.getDoubleExtra("longitude",0.0);
        String name = intent.getStringExtra("name");
        String arrival = intent.getStringExtra("arrival");
        String address = intent.getStringExtra("address");
        Log.d("Maps","Latitude " +lat + " Longitude " +lon +" name "+name + " arrival "+arrival + " address "+address);

        Current = new LatLng(lat, lon);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (!lat.equals(null) && !lon.equals(null) && !address.equals(null) && !name.equals(null) && !arrival.equals(null) ) {
            addr.setText(address);
            nameTxt.setText(name);
            la.setText(String.valueOf(lat));
            lo.setText(String.valueOf(lon));
            arive.setText(String.valueOf(arrival));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(Current).title("Location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Current));
    }
}