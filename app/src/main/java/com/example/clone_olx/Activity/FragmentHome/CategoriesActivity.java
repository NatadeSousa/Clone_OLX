package com.example.clone_olx.Activity.FragmentHome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.clone_olx.R;

public class CategoriesActivity extends AppCompatActivity {

    private ImageButton ibGetBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        referComponents();
        setClicks();

    }

    private void setClicks(){

        ibGetBack.setOnClickListener(view -> finish());

    }

    private void referComponents(){

        ibGetBack = findViewById(R.id.ib_get_back);

    }

}