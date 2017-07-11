package com.example.yijunchen.homes.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.yijunchen.homes.R;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yijunchen on 7/8/17.
 */

public class AddPropertyFragment extends Fragment {
    ImageView property_img1;
    Button addImg1, addImg2, addImg3;
    BootstrapButton addPropertyButton;
    TextView property_name, property_size, property_cost, property_category,
            property_type, property_add1, property_add2, property_zip, property_lat,
            property_long, property_des;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String url = "http://www.rjtmobile.com/realestate/register.php?property&add";
    public static final String PROPERTY_NAME = "propertyname";
    public static final String PROPERTY_SIZE = "propertysize";
    public static final String PROPERTY_ADDRESS1 = "propertyadd1";
    public static final String PROPERTY_ADDRESS2 = "propertyadd2";
    public static final String PROPERTY_COST = "propertycost";
    public static final String PROPERTY_CATEGORY = "propertycategory";
    public static final String PROPERTY_TYPE = "propertytype";
    public static final String PROPERTY_ZIP = "propertyzip";
    public static final String PROPERTY_LAT = "propertylat";
    public static final String PROPERTY_LONG = "propertylong";
    public static final String PROPERTY_DESCRIPTION = "propertydescription";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_property, container, false);
        property_name = (TextView) v.findViewById(R.id.add_property_name);
        property_size = (TextView) v.findViewById(R.id.add_property_size);
        property_add1 = (TextView) v.findViewById(R.id.add_property_address1);
        property_add2 = (TextView) v.findViewById(R.id.add_property_address2);
        property_zip = (TextView) v.findViewById(R.id.add_property_zip);
        property_lat = (TextView) v.findViewById(R.id.add_property_lat);
        property_long = (TextView) v.findViewById(R.id.add_property_long);
        property_des = (TextView) v.findViewById(R.id.add_property_des);
        property_cost = (TextView) v.findViewById(R.id.add_property_cost);
        property_category = (TextView) v.findViewById(R.id.add_property_cate);
        property_type = (TextView) v.findViewById(R.id.add_property_type);
        addPropertyButton = (BootstrapButton) v.findViewById(R.id.add_property_button);
        property_img1 = (ImageView) v.findViewById(R.id.add_property_img1);
        addImg1 = (Button) v.findViewById(R.id.add_property_img1_button);
        addImg2 = (Button) v.findViewById(R.id.add_property_img2_button);
        addImg3 = (Button) v.findViewById(R.id.add_property_img3_button);

        /* Add photo from gallery  */
        addImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        addImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        addImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        addPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProperty();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            property_img1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void addProperty() {
        final String name = property_name.getText().toString().trim();
        final String size = property_size.getText().toString().trim();
        final String cost = property_cost.getText().toString().trim();
        final String type = property_type.getText().toString().trim();
        final String category = property_category.getText().toString().trim();
        final String zip = property_zip.getText().toString().trim();
        final String add1 = property_add1.getText().toString().trim();
        final String add2 = property_add2.getText().toString().trim();
        final String lat = property_lat.getText().toString().trim();
        final String longitude = property_long.getText().toString().trim();
        final String des = property_des.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("bool(true)")) {
                            Toast.makeText(getActivity(), "Property success added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(PROPERTY_NAME, name);
                params.put(PROPERTY_SIZE, size);
                params.put(PROPERTY_ADDRESS1, add1);
                params.put(PROPERTY_ADDRESS2, add2);
                params.put(PROPERTY_TYPE, type);
                params.put(PROPERTY_COST, cost);
                params.put(PROPERTY_CATEGORY, category);
                params.put(PROPERTY_DESCRIPTION, des);
                params.put(PROPERTY_ZIP, zip);
                params.put(PROPERTY_LAT, lat);
                params.put(PROPERTY_LONG, longitude);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
