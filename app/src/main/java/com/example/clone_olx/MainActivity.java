package com.example.clone_olx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

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