package com.example.yijunchen.homes.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yijunchen.homes.helperClasses.PermissionUtils;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.activities.MainActivity;
import com.example.yijunchen.homes.models.Property;
import com.example.yijunchen.homes.models.PropertyList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.List;

/**
 * Created by yijunchen on 7/6/17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    Context context;
    FitWindowsFrameLayout short_view;
    Property target = new Property();
    List<Property> propertyList = PropertyList.getInstance();
    TextView filter;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();
        return mapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.map_fragment, container, false);
        short_view = (FitWindowsFrameLayout) v.findViewById(R.id.short_view);
        filter = (TextView) v.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, new FilterFragment()).commit();
                //transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        context = getContext();
        //propertyList = (List<Property>) getActivity().getIntent().getSerializableExtra("propertyList");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        IconGenerator iconFactory = new IconGenerator(getActivity());
        //iconFactory.setColor(Color.argb(0,34,139,34));
        //iconFactory.setStyle(IconGenerator.STYLE_GREEN);

        for (int i = 0; i < propertyList.size(); i++) {
            Property property = propertyList.get(i);
            LatLng pin1 = new LatLng(property.getLatitude(), property.getLongitude());
            if (property.getCategory() == 1) {
                iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
            } else if (property.getCategory() == 2) {
                iconFactory.setStyle(IconGenerator.STYLE_GREEN);
            } else if (property.getCategory() == 3) {
                iconFactory.setStyle(IconGenerator.STYLE_BLUE);
            }

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("$" + property.getCost())))
                    .position(pin1).anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV()));
            marker.setTag(property.getId());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pin1));
            mMap.setOnMarkerClickListener(this);

        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.838874, -87.857666), 9));
        enableMyLocation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("marker tag", marker.getTag().toString());
        int propertyId = (int) marker.getTag();
        for (Property property : propertyList) {
            if (property.getId() == propertyId) {
                target = property;
            }
        }
        ShortViewOfPropertyFragment shortViewOfPropertyFragment = new ShortViewOfPropertyFragment();
        Bundle args = new Bundle();
        args.putParcelable("property", target);
        shortViewOfPropertyFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.short_view, shortViewOfPropertyFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        short_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyDetailFragment propertyDetailFragment = new PropertyDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("property", target);
                propertyDetailFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, propertyDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return false;
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            MainActivity mainActivity = (MainActivity) getActivity();
            PermissionUtils.requestPermission(mainActivity, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}
