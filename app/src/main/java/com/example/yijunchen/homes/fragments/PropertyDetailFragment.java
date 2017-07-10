package com.example.yijunchen.homes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.models.Property;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yijunchen on 7/5/17.
 */

public class PropertyDetailFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    @BindView(R.id.property_detail_price)
    TextView getProperty_price;
    @BindView(R.id.property_detail_address)
    TextView getProperty_address;
    @BindView(R.id.property_detail_type)
    TextView getProperty_type;
    @BindView(R.id.property_detail_status)
    TextView getProperty_status;
    @BindView(R.id.property_detail_size)
    TextView getProperty_size;
    @BindView(R.id.property_detail_update)
    TextView getProperty_update;
    @BindView(R.id.property_detail_des)
    TextView getProperty_des;
    @BindView(R.id.share_icon)
    ImageView shareProperty;
    @BindView(R.id.schedule_tour_button)
    com.beardedhen.androidbootstrap.BootstrapButton schedule_tour;
    private SliderLayout mDemoSlider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.property_detail, container, false);
        ButterKnife.bind(this, v);
        Bundle bundle = this.getArguments();
        final Property property = bundle.getParcelable("property");
        Log.d("property detail", property.toString());

        getProperty_des.setText(property.getDescription());
        getProperty_price.setText("$" + property.getCost());
        getProperty_address.setText(property.getAddress1() + property.getAddress2());
        getProperty_size.setText(property.getSize() + "ft");
        getProperty_status.setText(property.getStatus());
        getProperty_type.setText(property.getType());
        getProperty_update.setText(property.getUpdate());

        mDemoSlider = (SliderLayout) v.findViewById(R.id.property_detail_slider);
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("2", property.getImgThumb2());
        url_maps.put("3", property.getImgThumb3());

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        schedule_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleATourFragment scheduleATourFragment = new ScheduleATourFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, scheduleATourFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        shareProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                i.putExtra(Intent.EXTRA_SUBJECT, "Share you a nice house!");
                i.putExtra(Intent.EXTRA_TEXT, "Hi, I found a very nice property. " +
                        "It's " + getProperty_des.getText() + "It has " + getProperty_size.getText() + ". It's located in " + getProperty_address.getText() + ". Only cost " + getProperty_price.getText()
                        + ". Come to check more detail.");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
