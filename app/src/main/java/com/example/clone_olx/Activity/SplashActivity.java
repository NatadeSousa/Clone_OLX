package com.example.clone_olx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.clone_olx.Helper.SPFilter;
import com.example.clone_olx.R;

public class SplashActivity extends AppCompatActivity {

    //Activity life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::changeActivity, 2500);
        SPFilter.cleanFilter(this);

    }
    //----------------------------------------------------------------------------------------------


    //Going to MainActivity
    private void changeActivity(){
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    //----------------------------------------------------------------------------------------------

}