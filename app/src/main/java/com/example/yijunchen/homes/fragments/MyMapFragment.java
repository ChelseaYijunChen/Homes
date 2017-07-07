package com.example.yijunchen.homes.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.BubbleIconFactory;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yijunchen on 7/6/17.
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback ,GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    //    List<Property> propertyList;
    Context context;
    MapView mapView;
    FitWindowsFrameLayout short_view;
    Property target = new Property();

    List<Property> propertyList = new ArrayList<>();
    String GET_JSON_DATA_HTTP_URL = "http://rjtmobile.com/aamir/realestate/realestate_app/getproperty.php";
    String JSON_PROPERTY_ID = "PropertyId";
    String JSON_PROPERTY_NAME = "PropertyName";
    String JSON_PROPERTY_DESC = "PropertyDesc";
    String JSON_PROPERTY_TYPE = "PropertyType";
    String JSON_PROPERTY_CATEGORY = "PropertyCategory";
    String JSON_PROPERTY_ADDRESS1 = "PropertyAddress1";
    String JSON_PROPERTY_ADDRESS2 = "PropertyAddress2";
    String JSON_PROPERTY_ZIP = "PropertyZip";
    String JSON_PROPERTY_LATITUDE = "PropertyLatitute";
    String JSON_PROPERTY_LONGITUDE = "PropertyLongtitue";
    String JSON_PROPERTY_THUMB1 = "PropertyThumb1";
    String JSON_PROPERTY_THUMB2 = "PropertyThumb2";
    String JSON_PROPERTY_THUMB3 = "PropertyThumb3";
    String JSON_PROPERTY_COST = "PropertyCost";
    String JSON_PROPERTY_SIZE = "PropertySize";
    String JSON_PROPERTY_STATUS = "PropertyStatus";
    String JSON_PROPERTY_UPDATE = "PropertyUpdate";
    String JSON_PROPERTY_SELLER_ID = "PropertySellerID";

    RequestQueue requestQueue;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;


    public static MyMapFragment newInstance() {
        MyMapFragment myMapFragment = new MyMapFragment();
        return myMapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (propertyList.size() == 0) {
            JSON_DATA_WEB_CALL();
        }

        View v = inflater.inflate(R.layout.map_fragment, container, false);
        short_view = (FitWindowsFrameLayout) v.findViewById(R.id.short_view);

        context = getContext();
        //propertyList = (List<Property>) getActivity().getIntent().getSerializableExtra("propertyList");

        Log.d("property size in map", propertyList.size() + "");

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
        iconFactory.setStyle(IconGenerator.STYLE_GREEN);
//        iconFactory.setStyle(IconGenerator.STYLE_GREEN);

        for (int i = 0; i < propertyList.size(); i++) {
            Property property = propertyList.get(i);
            Log.d("property position", property.getLatitude() + " " + property.getLongitude() + " ");
            LatLng pin1 = new LatLng(property.getLatitude(), property.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("$"+ property.getCost())))
                    .position(pin1).anchor(iconFactory.getAnchorU(),iconFactory.getAnchorV()));
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dialog3)));
            marker.setTag(property.getId());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pin1));
            mMap.setOnMarkerClickListener(this);

        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("marker tag", marker.getTag().toString());
        int propertyId = (int) marker.getTag();
        for (Property property: propertyList){
            if(property.getId()==propertyId){
                target = property;
            }
        }
        ShortViewOfPropertyFragment shortViewOfPropertyFragment = new ShortViewOfPropertyFragment();
        Bundle args = new Bundle();
        args.putParcelable("property",target);
        shortViewOfPropertyFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.short_view, shortViewOfPropertyFragment);
        transaction.commit();

        short_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyDetailFragment propertyDetailFragment = new PropertyDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("property",target);
                propertyDetailFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, propertyDetailFragment);
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

    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        mMap.addMarker(markerOptions);
    }

    public void JSON_DATA_WEB_CALL(){

        StringRequest stringRequest= new StringRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Property List");
                            Log.d("property json array", jsonArray.toString());
                            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        Log.d("property json array", "array length" + array.length());
        for (int i = 0; i < array.length(); i++) {

            Property property = new Property();

            try {
                JSONObject json = array.getJSONObject(i);
                property.setAddress1(json.getString(JSON_PROPERTY_ADDRESS1));
                property.setAddress2(json.getString(JSON_PROPERTY_ADDRESS2));
                property.setCategory(json.getInt(JSON_PROPERTY_CATEGORY));
                property.setCost(json.getDouble(JSON_PROPERTY_COST));
                property.setDescription(json.getString(JSON_PROPERTY_DESC));
                property.setId(json.getInt(JSON_PROPERTY_ID));
                property.setSellerId(json.getInt(JSON_PROPERTY_SELLER_ID));
                property.setImgThumb1(json.getString(JSON_PROPERTY_THUMB1));
                property.setImgThumb2(json.getString(JSON_PROPERTY_THUMB2));
                property.setImgThumb3(json.getString(JSON_PROPERTY_THUMB3));
                property.setLatitude(Float.parseFloat(json.getString(JSON_PROPERTY_LATITUDE)));
                property.setLongitude(Float.parseFloat(json.getString(JSON_PROPERTY_LONGITUDE)));
                property.setName(json.getString(JSON_PROPERTY_NAME));
                property.setSize(json.getInt(JSON_PROPERTY_SIZE));
                property.setStatus(json.getString(JSON_PROPERTY_STATUS));
                property.setZipCode(json.getInt(JSON_PROPERTY_ZIP));
                property.setType(json.getString(JSON_PROPERTY_TYPE));
                property.setUpdate(json.getString(JSON_PROPERTY_UPDATE));
                //Log.d("property json array", "property : "+property.toString());

            } catch (JSONException e) {

                e.printStackTrace();
            }
            propertyList.add(property);

        }
    }
}
