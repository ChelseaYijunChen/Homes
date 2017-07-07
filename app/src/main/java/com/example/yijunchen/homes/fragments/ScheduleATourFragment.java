package com.example.yijunchen.homes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.yijunchen.homes.R;

/**
 * Created by yijunchen on 7/6/17.
 */

public class ScheduleATourFragment extends Fragment{
    BootstrapButton sendEmail;
    EditText getRequest_name, getRequest_phone, getRequest_email, getRequest_body;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schedule_a_tour,container,false);

        getRequest_email = (EditText) v.findViewById(R.id.request_email);
        getRequest_name = (EditText) v.findViewById(R.id.request_name);
        getRequest_phone = (EditText) v.findViewById(R.id.request_phone);
        getRequest_body = (EditText) v.findViewById(R.id.request_body);
        sendEmail = (BootstrapButton) v.findViewById(R.id.schedule_tour_button);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getRequest_email.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, "request a tour from "+ getRequest_name.getText());
                i.putExtra(Intent.EXTRA_TEXT   , getRequest_body.getText());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
