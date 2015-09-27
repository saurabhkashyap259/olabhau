package com.mate.olabhau.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mate.olabhau.R;

public class MapFragment extends Fragment {


    private GoogleMap mMap;

    private static final String LOGTAG = HomeFragment.class.getSimpleName();

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        context = getActivity().getApplicationContext();

       View map =  view.findViewById(R.id.mapFragment);

        setUpMapIfNeeded();

        return view;
    }


    private void setUpMapIfNeeded() {

//        progressDialog = new ProgressDialog(MapsActivity.this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("Loading. Please wait...");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            FragmentManager fm = getChildFragmentManager();

            mMap = ((SupportMapFragment) fm.findFragmentById(R.id.mapFragment))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }else{
            //progressDialog.hide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(12.95, 77.65)).title("Marker"));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d(LOGTAG, "onMapLoaded");
                //progressDialog.hide();
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.95, 77.64), 15));
        mMap.setMyLocationEnabled(true);
    }

    public static MapFragment newInstance(String text) {

        MapFragment f = new MapFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
