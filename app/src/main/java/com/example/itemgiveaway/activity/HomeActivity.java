package com.example.itemgiveaway.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.model.PagerView;
import com.example.itemgiveaway.services.LocationService;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout about, donate, needy, chat, account;
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
        viewPager = (ViewPager) findViewById(R.id.fragment_control);

        about.setOnClickListener(this);
        chat.setOnClickListener(this);
        donate.setOnClickListener(this);
        needy.setOnClickListener(this);
        account.setOnClickListener(this);
        LocationService locationService=new LocationService(this);
        locationService.getLocation();
        pagerView = new PagerView(getSupportFragmentManager());
        viewPager.setAdapter(pagerView);
        viewPager.setCurrentItem(2);
        onChangeTab(2);

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

    private void onChangeTab(int i) {
        String selectedColor = "#33c9dc";
        String unselectedColor = "#FFFFFF";
        about.setBackgroundColor(Color.parseColor(unselectedColor));
        chat.setBackgroundColor(Color.parseColor(unselectedColor));
        donate.setBackgroundColor(Color.parseColor(unselectedColor));
        needy.setBackgroundColor(Color.parseColor(unselectedColor));
        account.setBackgroundColor(Color.parseColor(unselectedColor));
        if (i == 0) {
            about.setBackgroundColor(Color.parseColor(selectedColor));
        } else if (i == 1) {
            chat.setBackgroundColor(Color.parseColor(selectedColor));
        } else if (i == 2) {
            donate.setBackgroundColor(Color.parseColor(selectedColor));
        } else if (i == 3) {
            needy.setBackgroundColor(Color.parseColor(selectedColor));
        } else if (i == 4) {
            account.setBackgroundColor(Color.parseColor(selectedColor));
        }
    }


    @Override
    public void onClick(View view) {
        int selected;//default will be home
        if (view == about) {
            selected = 0;
        } else if (view == chat) {
            selected = 1;
        } else if (view == donate) {
            selected = 2;
        } else if (view == needy) {
            selected = 3;
        } else {
            selected = 4;
        }
        viewPager.setCurrentItem(selected);
    }
}