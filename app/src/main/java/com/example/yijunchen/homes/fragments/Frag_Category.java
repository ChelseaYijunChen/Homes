package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.example.yijunchen.homes.adapters.HomeViewPagerAdapter;
import com.example.yijunchen.homes.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zhangwenpurdue on 7/7/2017.
 */

public class Frag_Category extends Fragment {
    private static final String KEY = "extra";
    private final String BASE_URL = "http://rjtmobile.com/aamir/realestate/realestate_app/get_pro_cat.php?";
    public ArrayList<Category> mList ;
    TabLayout tabLayout;
    ViewPager viewPager;
    HomeViewPagerAdapter homeViewPagerAdapter;
    private ArrayList<String> mTitles;
    ArrayList<All_Property_Test> mViewPagerFragments;
    Frag_Category one, two, three;
    RequestQueue requestQueue ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_category_layout, container, false);
        mTitles = new ArrayList<>();
        mList = new ArrayList<>();
        tabLayout = (TabLayout) view.findViewById(R.id.home_tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.home_viewpager);

        fetchData();
        return view;
    }
    private void fetchData() {
        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray categories = new JSONObject(response).getJSONArray("Property Category");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject item = categories.getJSONObject(i);
                        Category category = new Category();
                        if (item.has("PropertyId")) {
                            String gId = item.getString("PropertyId");
                            category.setId(gId);
                        }
                        if (item.has("PropertyName")) {
                            String gName = item.getString("PropertyName");
                            category.setName(gName);
                        }
                        if (item.has("PropertyDesc")) {
                            String desc = item.getString("PropertyDesc");
                            category.setDesc(desc);
                        }
                        if (item.has("PropertyStatus")) {
                            String status = item.getString("PropertyStatus");
                            category.setStatus(status);
                        }
                        Toast.makeText(getActivity(), category.getId(), Toast.LENGTH_SHORT).show();
                        mList.add(category);

                    }
                    String[] temp = new String[mList.size()];
                    for (int i = 0; i < mList.size(); i++) {
                        temp[i] = mList.get(i).getName();
                    }
                    mViewPagerFragments = new ArrayList<All_Property_Test>();
                    homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity().getSupportFragmentManager());

                    Toast.makeText(getActivity(), mList.size() + "", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < mList.size(); i++) {
                        All_Property_Test all_property_test = new All_Property_Test();
                       // Frag_Category frag_category = Frag_Category.newInstance( mList.get(i).getmGroceryId());
                        ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();

                        Bundle bundle = new Bundle();
                        bundle.putString("CategoryID", mList.get(i).getId());
                        all_property_test.setArguments(bundle);
                        mViewPagerFragments.add(all_property_test);
                    }
                    homeViewPagerAdapter.setTitles(temp);
                    // homeViewPagerAdapter.setTitles(mTitles.toArray());
                    homeViewPagerAdapter.setFragments(mViewPagerFragments);


                    viewPager.setAdapter(homeViewPagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);

                    // mRecyclerView.setAdapter(new ProductAdapter(getActivity(), mList));
                    //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
