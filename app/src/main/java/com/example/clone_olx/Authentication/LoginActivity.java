package com.example.clone_olx.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.clone_olx.R;

public class LoginActivity extends AppCompatActivity {

    private ImageButton ibGetBack;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referComponents();
        setClicks();
        validateData();

    }
    //-----------------------------------------------------------------


    //Validating data that user typed
    private void validateData(){

    }
    //-----------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

    }
    //-----------------------------------------------------------------

    //Referring components
    private void referComponents(){
        ibGetBack = findViewById(R.id.ib_get_back);
    }
    //-----------------------------------------------------------------
}