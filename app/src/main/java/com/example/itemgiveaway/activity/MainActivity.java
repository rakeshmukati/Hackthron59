package com.example.itemgiveaway.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.utils.AuthenticationManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AuthenticationManager.getInstance().isAuthorised()){
            //todo user already logged in
        }else {
            //todo user not logged in
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        },1500);
    }
}