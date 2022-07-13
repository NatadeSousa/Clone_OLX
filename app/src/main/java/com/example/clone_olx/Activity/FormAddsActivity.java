package com.example.clone_olx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.clone_olx.R;

public class FormAddsActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private Button btnCreateAdd;

    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_adds);

        referComponents();
        setClicks();

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        btnCreateAdd.setOnClickListener(view -> {
            Toast.makeText(this, "Anúncio Salvo!", Toast.LENGTH_SHORT).show();
        });
        ibGetBack.setOnClickListener(view -> finish());

    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        btnCreateAdd = findViewById(R.id.btn_create_add);
        ibGetBack = findViewById(R.id.ib_get_back);

    }
    //--------------------------------------------------------------------------------------

}