package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.adapters.RecycleViewAdapter_SubCategory;
import com.example.yijunchen.homes.models.Property;
import com.example.yijunchen.homes.models.PropertyList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yijunchen on 7/9/17.
 */

public class TabOneFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mDemoSlider;
    private static Bundle mBundleRecyclerviewState;
    private final String keyRecyclerviewState = "recycler_state";
    private final String RECYCLERVIEW_POSITION = "POSITIONS";
    private final String TAG = TabOneFragment.class.getCanonicalName();
    ArrayList<Property> propertyList = new ArrayList<>();
    //List<Property> propertyList2 = PropertyList.getInstance();
    //List<Property> rentPropertyList = new ArrayList<>();

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

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager = new LinearLayoutManager(getContext());
    RecycleViewAdapter_SubCategory recycleViewAdapter_subCategory;
    RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(propertyList.size() == 0){
            JSON_DATA_WEB_CALL();
        }
//        if (mBundleRecyclerviewState == null) {
//            JSON_DATA_WEB_CALL();
//        } else {
//            restorePreviousState();
//        }

        View v = inflater.inflate(R.layout.category_one, container, false);

        mDemoSlider = (SliderLayout) v.findViewById(R.id.rent_slider);
        showSlider(mDemoSlider);

//        for (int i = 0; i < propertyList2.size(); i++) {
//            Log.v("tab one fragment", propertyList2.get(i).toString());
//        }
        recyclerView = (RecyclerView) v.findViewById(R.id.rent_all_property_recycleview);
        recycleViewAdapter_subCategory = new RecycleViewAdapter_SubCategory(propertyList, getContext());
        recycleViewAdapter_subCategory.setOnItemClickListener(new RecycleViewAdapter_SubCategory.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(getActivity(), recyclerView.getChildAdapterPosition(view) + "", Toast.LENGTH_LONG).show();
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
        recycleViewAdapter_subCategory.notifyDataSetChanged();
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        recyclerView.setHasFixedSize(true);

        return v;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mBundleRecyclerviewState = outState;
//        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
//        mBundleRecyclerviewState.putParcelable(keyRecyclerviewState, listState);
//        mBundleRecyclerviewState.putParcelableArrayList(RECYCLERVIEW_POSITION, propertyList2);
//    }
//
//    //Restore saved recyclerview items
//    private void restorePreviousState() {
//        Parcelable listState = mBundleRecyclerviewState.getParcelable(keyRecyclerviewState);
//        propertyList2 = mBundleRecyclerviewState.getParcelableArrayList(RECYCLERVIEW_POSITION);
//        recyclerViewlayoutManager.onRestoreInstanceState(listState);
//        Log.d(TAG, "restore list" + propertyList2.toString());
//        Log.d(TAG, "restore called");
//    }

    public void showSlider(SliderLayout sliderLayout) {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("$2,190,000", R.drawable.house14);
        file_maps.put("$3,190,000", R.drawable.house2);
        file_maps.put("$898,000", R.drawable.house1);
        file_maps.put("$390,000", R.drawable.house15);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3500);
        sliderLayout.addOnPageChangeListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void JSON_DATA_WEB_CALL() {

        StringRequest stringRequest = new StringRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Property List");
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
