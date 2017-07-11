package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.adapters.RecycleViewAdapter_SubCategory;
import com.example.yijunchen.homes.models.Property;
import com.example.yijunchen.homes.models.PropertyList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yijunchen on 7/2/17.
 */

public class AllPropertyFragment extends Fragment {
    List<Property> propertyList = PropertyList.getInstance();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecycleViewAdapter_SubCategory recycleViewAdapter_subCategory;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (propertyList.size() == 0) {
            JSON_DATA_WEB_CALL();
        }
        View v = inflater.inflate(R.layout.list_all_property, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.all_property_recycleview);
        recycleViewAdapter_subCategory = new RecycleViewAdapter_SubCategory(propertyList, getContext());
        recycleViewAdapter_subCategory.setOnItemClickListener(new RecycleViewAdapter_SubCategory.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                PropertyDetailFragment propertyDetailFragment = new PropertyDetailFragment();
                Bundle args = new Bundle();
                int position = recyclerView.getChildAdapterPosition(view);
                Property property = propertyList.get(position);
                args.putParcelable("property", property);
                propertyDetailFragment.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, propertyDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
        recyclerView.setAdapter(recycleViewAdapter_subCategory);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerView.setHasFixedSize(true);

        return v;
    }

    public void JSON_DATA_WEB_CALL() {

        StringRequest stringRequest = new StringRequest(GET_JSON_DATA_HTTP_URL,

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


            } catch (JSONException e) {

                e.printStackTrace();
            }
            propertyList.add(property);
            recycleViewAdapter_subCategory.notifyDataSetChanged();
        }
    }
}
