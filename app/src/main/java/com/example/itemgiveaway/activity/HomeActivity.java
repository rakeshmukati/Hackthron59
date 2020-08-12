package com.example.itemgiveaway.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.model.PagerView;

public class HomeActivity extends AppCompatActivity {
    LinearLayout about,donate,needy,chat,account;
    ViewPager viewPager;
    PagerView pagerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        about = findViewById(R.id.about);
        donate = findViewById(R.id.donate);
        needy = findViewById(R.id.needy);
        chat = findViewById(R.id.chat);
        account = findViewById(R.id.account);
        viewPager = (ViewPager)findViewById(R.id.fragment_control);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        needy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4);
            }
        });

        pagerView = new PagerView(getSupportFragmentManager());
        viewPager.setAdapter(pagerView);
        viewPager.setCurrentItem(2);
        donate.setBackgroundColor(Color.parseColor("#03DAC5"));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                onChangeTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    private void onChangeTab(int i){
        if (i==0){
            about.setBackgroundColor(Color.parseColor("#03DAC5"));
            chat.setBackgroundColor(Color.parseColor("#ffffff"));
            donate.setBackgroundColor(Color.parseColor("#ffffff"));
            needy.setBackgroundColor(Color.parseColor("#ffffff"));
            account.setBackgroundColor(Color.parseColor("#ffffff"));
        }
       if(i==1){
           about.setBackgroundColor(Color.parseColor("#ffffff"));
           chat.setBackgroundColor(Color.parseColor("#03DAC5"));
           donate.setBackgroundColor(Color.parseColor("#ffffff"));
           needy.setBackgroundColor(Color.parseColor("#ffffff"));
           account.setBackgroundColor(Color.parseColor("#ffffff"));
       }
       if (i==2){
           about.setBackgroundColor(Color.parseColor("#ffffff"));
           chat.setBackgroundColor(Color.parseColor("#ffffff"));
           donate.setBackgroundColor(Color.parseColor("#03DAC5"));
           needy.setBackgroundColor(Color.parseColor("#ffffff"));
           account.setBackgroundColor(Color.parseColor("#ffffff"));
       }
        if(i==3){
            about.setBackgroundColor(Color.parseColor("#ffffff"));
            chat.setBackgroundColor(Color.parseColor("#ffffff"));
            donate.setBackgroundColor(Color.parseColor("#ffffff"));
            needy.setBackgroundColor(Color.parseColor("#03DAC5"));
            account.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        if (i==4){
            about.setBackgroundColor(Color.parseColor("#ffffff"));
            chat.setBackgroundColor(Color.parseColor("#ffffff"));
            donate.setBackgroundColor(Color.parseColor("#ffffff"));
            needy.setBackgroundColor(Color.parseColor("#ffffff"));
            account.setBackgroundColor(Color.parseColor("#03DAC5"));
        }
    }
}