package com.example.yijunchen.homes.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.yijunchen.homes.R;

public class SplashScreenActivity extends Activity implements Animation.AnimationListener{
    private int splashInterval = 3000;
    ImageView light1, light2;
//    Button seller;
//    Button buyer;
    BootstrapButton seller, buyer;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        animation.setAnimationListener(this);
        seller = (BootstrapButton) findViewById(R.id.seller);
        buyer = (BootstrapButton) findViewById(R.id.browsing);
      //  light1.startAnimation(animation);
       // light2.startAnimation(animation);
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, SellerActivity.class);
                startActivity(intent);
            }
        });
        buyer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
