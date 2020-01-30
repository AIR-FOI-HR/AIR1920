package com.example.maps;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.database.EntitiesFirebase.Znamenitost;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapModule extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    SupportMapFragment mapFragment;
    private List<Znamenitost> znamenitosti;

    private boolean moduleReadyFlag = false;
    private boolean dataReadyFlag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        System.out.println(("HOLA2 ViewCreated"));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(("HOLA2 ViewCreated"));

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);

        //mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }
    */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        moduleReadyFlag = true;
        //tryToDisplayData();
        System.out.println(("OnMapReady"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.7750, 122.4183)).title("San Francisco"));
        mockData();
    }

    private void mockData() {
        LatLng sydney = new LatLng(0, 0);
        //map.addMarker(new MarkerOptions().position(new LatLng(37.7750, 122.4183)).title("San Francisco"));
        //map.addMarker(new MarkerOptions().position(new LatLng(37.7750, 122.4183)).title("San Francisco").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //if(map != null) {
        System.out.println(("MockData"));
            //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //}
    }

    public void tryToDisplayData() {
        if (moduleReadyFlag && dataReadyFlag) {
            displayData();
        }
    }

    private void displayData() {
        boolean cameraReady = false;
        if(znamenitosti != null)
            for (Znamenitost z: znamenitosti) {
                LatLng position =
                        new LatLng(z.getLatitude() / 1000000d,
                                z.getLongitude() / 1000000d);
                map.addMarker(
                        new MarkerOptions()
                                .position(position)
                                .title(z.getNaziv()));
                if (!cameraReady) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(position));
                    map.moveCamera(CameraUpdateFactory.zoomTo(12));
                    cameraReady = true;
                }
            }
    }
}
