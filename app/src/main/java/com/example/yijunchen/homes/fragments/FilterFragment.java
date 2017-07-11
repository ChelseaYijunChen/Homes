package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yijunchen.homes.R;

/**
 * Created by zhangwenpurdue on 7/7/2017.
 */

public class FilterFragment extends Fragment {
    TextView apply_filter;
    TextInputEditText property_name;
    TextInputEditText property_location;
    RadioGroup property_type_group;
    RadioGroup property_type_name_group;

    StringBuilder url = new StringBuilder("http://rjtmobile.com/aamir/realestate/realestate_app/search_pro.php?");
    String pptyname = "&pptyname=";
    String pptylocation = "&pptylocation=";
    String pptype = "&pptype=";
    String pcatid = "&pcatid=";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.filter_layout, container, false);
        apply_filter = (TextView) view.findViewById(R.id.filter_apply);
        property_type_group = (RadioGroup) view.findViewById(R.id.property_type);
        property_type_name_group = (RadioGroup) view.findViewById(R.id.property_type_name);
        property_name = (TextInputEditText) view.findViewById(R.id.property_name);
        property_location = (TextInputEditText) view.findViewById(R.id.property_location);
        property_type_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) view.findViewById(checkedId);
                String catid = radioButton.getText().toString();
                pcatid = "&pcatid=";
                if (catid.equals("All")) {
                    pcatid = "";
                } else if (catid.equals("Rent")) {
                    pcatid += "1";
                } else if (catid.equals("Outright Purchase")) {
                    pcatid += "2";
                } else if (catid.equals("Mortgage")) {
                    pcatid += "3";
                }
            }
        });

        property_type_name_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) view.findViewById(checkedId);
                String type_name = radioButton.getText().toString();
                pptype = "&pptype=";
                if (type_name.equals("All Type")) {
                    pptype = "";
                } else if (type_name.equals("Bar House")) {
                    pptype += "Bar House";

                } else if (type_name.equals("Food House")) {
                    pptype += "Food House";
                } else if (type_name.equals("House")) {
                    pptype += "House";
                } else if (type_name.equals("Dessert House")) {
                    pptype += "Dessert House";
                } else if (type_name.equals("Mansion")) {
                    pptype += "Mansion";
                } else if (type_name.equals("Home")) {
                    pptype += "Home";
                } else if (type_name.equals("Lake House")) {
                    pptype += "Lake House";
                } else if (type_name.equals("Boat House")) {
                    pptype += "Boat House";
                } else if (type_name.equals("Party House")) {
                    pptype += "Party House";
                } else if (type_name.equals("Stretch House")) {
                    pptype += "Stretch House";
                } else if (type_name.equals("White House")) {
                    pptype += "White House";
                } else if (type_name.equals("Home Theatre")) {
                    pptype += "Home Theatre";
                }
            }
        });

        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (property_name.getText().equals("")) {
                    pptyname = "";
                } else {
                    pptyname += property_name.getText().toString();
                }
                System.out.print(pptyname);

                if (property_location.getText().equals("")) {
                    pptylocation = "";
                } else {
                    pptylocation += property_location.getText().toString();
                }
                url.append(pptyname);
                url.append(pptylocation);
                url.append(pptype);
                url.append(pcatid);
                Bundle bundle = new Bundle();
                bundle.putString("url", url.toString());
                FilteredResultFragment filtered_resultFragment = new FilteredResultFragment();
                filtered_resultFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, filtered_resultFragment).commit();
            }
        });

        return view;
    }
}
