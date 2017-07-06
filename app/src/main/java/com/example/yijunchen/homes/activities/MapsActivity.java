package com.example.yijunchen.homes.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.models.Property;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Property> propertyList;
    String GET_JSON_DATA_HTTP_URL = "http://rjtmobile.com/aamir/realestate/realestate_app/getproperty.php";
    String JSON_PROPERTY_ID = "PropertyId";
    String JSON_PROPERTY_NAME = "PropertyName";
    String JSON_PROPERTY_DESC = "PropertyDesc";
    String JSON_PROPERTY_TYPE = "PropertyType";
    String JSON_PROPERTY_CATEGORY ="PropertyCategory";
    String JSON_PROPERTY_ADDRESS1 ="PropertyAddress1";
    String JSON_PROPERTY_ADDRESS2 ="PropertyAddress2";
    String JSON_PROPERTY_ZIP ="PropertyZip";
    String JSON_PROPERTY_LATITUDE ="PropertyLatitute";
    String JSON_PROPERTY_LONGITUDE ="PropertyLongtitue";
    String JSON_PROPERTY_THUMB1 ="PropertyThumb1";
    String JSON_PROPERTY_THUMB2 ="PropertyThumb2";
    String JSON_PROPERTY_THUMB3 ="PropertyThumb3";
    String JSON_PROPERTY_COST ="PropertyCost";
    String JSON_PROPERTY_SIZE ="PropertySize";
    String JSON_PROPERTY_STATUS ="PropertyStatus";
    String JSON_PROPERTY_UPDATE ="PropertyUpdate";
    String JSON_PROPERTY_SELLER_ID ="PropertySellerID";

    RequestQueue requestQueue ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(propertyList.size() == 0){
            JSON_DATA_WEB_CALL();
        }

        setContentView(R.layout.activity_maps);



        //propertyList = (List<Property>) getIntent().getSerializableExtra("propertyList");

        //Log.d("property size in map", propertyList.size() +"");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        for(int i=0; i< propertyList.size(); i++){
            Property property = propertyList.get(i);
            Log.d("property position", property.getLatitude() +" " + property.getLongitude() +" ");
            LatLng pin1 = new LatLng(property.getLatitude(), property.getLongitude());
            mMap.addMarker(new MarkerOptions().position(pin1).title(property.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pin1));
        }


//        LatLng pin1 = new LatLng(propertyList.get(0).getLatitude(), propertyList.get(0).getLongitude());
//        LatLng pin2 = new LatLng(propertyList.get(1).getLatitude(), propertyList.get(1).getLongitude());
//        Log.d("property position", propertyList.get(0).getLatitude() +" " + propertyList.get(0).getLongitude() +" ");
//        mMap.addMarker(new MarkerOptions().position(pin1).title(propertyList.get(0).getName()));
//        mMap.addMarker(new MarkerOptions().position(pin2).title(propertyList.get(2).getName()));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin1));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(pin2));

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

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        Log.d("property json array", "array length"+array.length());
        for(int i = 0; i<array.length(); i++) {

            Property property= new Property();

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
            //recycleViewAdapter_subCategory.notifyDataSetChanged();

            //Log.d("property json array", "list size"+propertyList.size());
            //recyclerViewadapter.notifyDataSetChanged();
        }

//        Intent intent = new Intent(getActivity(), MapsActivity.class);
//        intent.putExtra("propertyList", (Serializable) propertyList);
//
//        startActivity(intent);

    }
}
