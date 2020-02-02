package com.example.maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.database.EntitiesFirebase.Znamenitost;
import com.example.database.Listeners.ZnamenitostListener;
import com.example.database.ZnamenitostiHelper;
import com.example.default_znamenitost.DefaultZnamenitostActivity;
import com.example.kino_znamenitost.KinoActivity;
import com.example.setaliste_znamenitost.SetalisteActivity;
import com.example.spomenik_znamenitost.SpomenikActivity;
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
    private Znamenitost selectedZnamenitost = null;
    private View itemView;
    private MarkerTag markerTag;

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
        this.itemView = view;

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
                markerTag = new MarkerTag();
                LatLng position = new LatLng(z.getLatitude(), z.getLongitude());

                markerTag.setId(z.getIdZnamenitosti());
                markerTag.setIdKategorija(z.getIdKategorijaZnamenitosti());

                Marker marker = map.addMarker(new MarkerOptions().position(position).title(z.getNaziv()));

                marker.showInfoWindow();
                marker.setTag(markerTag);

                if (!cameraReady) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(position));
                    map.moveCamera(CameraUpdateFactory.zoomTo(12));
                    cameraReady = true;
                }
            }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MarkerTag markerTag1 = new MarkerTag();
                Intent intent = null;

                if(marker.getTag() instanceof MarkerTag){
                    markerTag1 = (MarkerTag) marker.getTag();
                }
                int kategorija = markerTag1.getIdKategorija();

                switch (kategorija){
                    case 1:
                        intent = postaviIntent(DefaultZnamenitostActivity.class);
                        break;
                    case 3:
                        intent = postaviIntent(SpomenikActivity.class);
                        break;
                    case 4:
                        intent = postaviIntent(SetalisteActivity.class);
                        break;
                    case 6:
                        intent = postaviIntent(KinoActivity.class);
                        break;
                    default:
                        intent = postaviIntent(DefaultZnamenitostActivity.class);
                }

                if (!intent.equals(null)){
                    intent.putExtra("id_znamenitost", markerTag.getId());

                    itemView.getContext().startActivity(intent);
                    return true;
                }

                return false;
            }
        });
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


    private Intent postaviIntent(Class activity){
        Intent intent = new Intent(itemView.getContext(), activity);

        return intent;
    }
}
