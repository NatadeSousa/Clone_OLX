package com.example.clone_olx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.clone_olx.R;

public class MyAddressActivity extends AppCompatActivity {

    private Button btnSave;
    private ImageButton ibGetBack;
    private ProgressBar pbMyAddressActivity;
    private EditText editCep,editUf,editCity,editNeighborhood;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        referComponents();
        setClicks();

    }
    //--------------------------------------------------------------------

    //Validating data provided by current user
    private void validateData(){

    }
    //--------------------------------------------------------------------

    //Setting clicks on Buttons
    private void setClicks(){
        btnSave.setOnClickListener(view -> {
            btnSave.setVisibility(View.INVISIBLE);
            pbMyAddressActivity.setVisibility(View.VISIBLE);

            validateData();
        });

        ibGetBack.setOnClickListener(view -> finish());
    }
    //--------------------------------------------------------------------

    //Referring components
    private void referComponents(){
        btnSave = findViewById(R.id.btn_save);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbMyAddressActivity = findViewById(R.id.pb_my_address_activity);
        editCep = findViewById(R.id.edit_cep);
        editNeighborhood = findViewById(R.id.edit_neighborhood);
        editCity = findViewById(R.id.edit_city);
        editUf  = findViewById(R.id.edit_uf);
    }
    //--------------------------------------------------------------------

}