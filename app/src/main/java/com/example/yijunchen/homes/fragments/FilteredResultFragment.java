package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.adapters.RecycleViewAdapter_SubCategory;
import com.example.yijunchen.homes.models.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwenpurdue on 7/10/2017.
 */

public class FilteredResultFragment extends Fragment {
    List<Property> propertyList = new ArrayList<Property>();
    RequestQueue requestQueue;
    static String URL;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecycleViewAdapter_SubCategory recycleViewAdapter_subCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_all_property, container, false);
        Bundle bundle = getArguments();
        URL = (String.valueOf(bundle.getString("url")));
        System.out.print(URL);
        Log.d("url", String.valueOf(bundle.getString("url")));
        recyclerView = (RecyclerView) view.findViewById(R.id.all_property_recycleview);
        fetchData();

        return view;
    }

    void fetchData() {
        requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray properties = new JSONObject(response).getJSONArray("Property Searched");

                    for (int i = 0; i < properties.length(); i++) {
                        JSONObject item = properties.getJSONObject(i);
                        Property property = new Property();
                        if (item.has("PropertyId")) {
                            String temp = item.getString("PropertyId");
                            property.setId(Integer.parseInt(temp));
                        }
                        if (item.has("PropertyName")) {
                            String temp = item.getString("PropertyName");
                            property.setName(temp);
                        }
                        if (item.has("PropertyDesc")) {
                            String temp = item.getString("PropertyDesc");
                            property.setDescription(temp);
                        }
                        if (item.has("PropertyType")) {
                            String temp = item.getString("PropertyType");
                            property.setType(temp);

                        }
                        if (item.has("PropertyCategory")) {
                            String temp = item.getString("PropertyCategory");
                            if (temp.equals("null")) {
                                property.setCategory(0);
                            } else {
                                property.setCategory(Integer.parseInt(temp));
                            }
                        }
                        if (item.has("PropertyAddress1")) {
                            String temp = item.getString("PropertyAddress1");
                            if (temp.equals("null")) {
                                property.setAddress1("");
                            } else {
                                property.setAddress1(temp);
                            }
                        }
                        if (item.has("PropertyAddress2")) {
                            String temp = item.getString("PropertyAddress2");
                            property.setAddress2(temp);
                        }
                        if (item.has("PropertyZip")) {
                            String temp = item.getString("PropertyZip");
                            property.setZipCode(Integer.parseInt(temp));
                        }
                        if (item.has("PropertyLatitute")) {
                            String temp = item.getString("PropertyLatitute");
                            property.setLatitude(Float.parseFloat(temp));
                        }
                        if (item.has("PropertyLongtitue")) {
                            String temp = item.getString("PropertyLongtitue");
                            property.setLongitude(Float.parseFloat(temp));
                        }
                        if (item.has("PropertyThumb1")) {
                            String temp = item.getString("PropertyThumb1");
                            property.setImgThumb1(temp);
                        }
                        if (item.has("PropertyThumb2")) {
                            String temp = item.getString("PropertyThumb2");
                            property.setImgThumb2(temp);
                        }
                        if (item.has("PropertyThumb3")) {
                            String temp = item.getString("PropertyThumb3");
                            property.setImgThumb3(temp);
                        }
                        if (item.has("PropertyCost")) {
                            String temp = item.getString("PropertyCost");
                            property.setCost(Double.parseDouble(temp));
                        }
                        if (item.has("PropertySize")) {
                            String temp = item.getString("PropertySize");
                            property.setSize(Integer.parseInt(temp));
                        }
                        if (item.has("PropertyStatus")) {
                            String temp = item.getString("PropertyStatus");
                            property.setStatus(temp);
                        }
                        if (item.has("PropertyUpdate")) {
                            String temp = item.getString("PropertyUpdate");
                            property.setUpdate(temp);
                        }
                        if (item.has("PropertySellerID")) {
                            String temp = item.getString("PropertySellerID");
                            property.setSellerId(Integer.parseInt(temp));
                        }
                        propertyList.add(property);
                    }
                    if (propertyList.size() == 0) {
                        Toast.makeText(getActivity(), "there is no results", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("filter list ==", propertyList.size() + " ");
                        recycleViewAdapter_subCategory = new RecycleViewAdapter_SubCategory(propertyList, getContext());
                        recycleViewAdapter_subCategory.notifyDataSetChanged();
                        recyclerView.setAdapter(recycleViewAdapter_subCategory);
                        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(recyclerViewlayoutManager);
                        recyclerView.setHasFixedSize(true);
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
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "exception");
            }
        });
        requestQueue.add(stringRequest);
    }
}

