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
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.ZnamenitostiHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapModule extends Fragment implements OnMapReadyCallback, ZnamenitostListener {
    GoogleMap map;
    SupportMapFragment mapFragment;
    private List<Znamenitost> znamenitosti;

    private boolean moduleReadyFlag = false;
    private boolean dataReadyFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        moduleReadyFlag = true;

        getData();
    }

    private void getData() {
        ZnamenitostiHelper znamenitostiHelper = new ZnamenitostiHelper(getActivity(), this);
        znamenitostiHelper.dohvatiSveZnamenitosti();
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

    @Override
    public void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti) {
        znamenitosti = listaZnamenitosti;
        dataReadyFlag = true;
        tryToDisplayData();
    }

    @Override
    public void onLoadZnamenitostFail(String message) {
        Log.d("ZnamenitostTag", "Load Fail");
    }
}
