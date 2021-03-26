package com.example.g16_listtrip.Activitys;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.g16_listtrip.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GPS extends Fragment implements  OnMapReadyCallback {
    private View rootView;
    private Location location;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    long curentLat = 0, curentLong = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gps, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng a = new LatLng(curentLat,curentLong);
        googleMap.addMarker(new MarkerOptions().position(a).title("Bạn đang ở đây!"));
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(a));
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            curentLong = (long) location.getLongitude();
            curentLat = (long) location.getLatitude();
        }
    };
}
