package com.example.yijunchen.homes.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yijunchen.homes.R;

/**
 * Created by yijunchen on 7/8/17.
 */

public class SellerAccountFragment extends Fragment{
    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.seller_account,container,false);
        cardView = (CardView) v.findViewById(R.id.add_property);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.main_fragment_container, new AddPropertyFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return v;
    }
}
