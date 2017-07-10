package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.models.Property;
import com.squareup.picasso.Picasso;

/**
 * Created by yijunchen on 7/7/17.
 */

public class ShortViewOfPropertyFragment extends Fragment {
    ImageView propertyImg;
    TextView short_view_property_detail, short_view_property_price;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.short_view_of_property, container, false);
        Bundle bundle = this.getArguments();
        final Property property = bundle.getParcelable("property");

        propertyImg = (ImageView) v.findViewById(R.id.short_view_property_img);
        short_view_property_detail = (TextView) v.findViewById(R.id.short_view_property_detail);
        short_view_property_price = (TextView) v.findViewById(R.id.short_view_property_price);

        Picasso.with(getContext()).load(property.getImgThumb3()).into(propertyImg);
        short_view_property_price.setText("$" + property.getCost());
        short_view_property_detail.setText(property.getSize() + "Sq ft . " + property.getAddress1() + "." + property.getAddress2());
        return v;
    }
}
