package com.example.clone_olx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.R;

public class FormAddsActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private Button btnCreateAdd;
    private ProgressBar pbFormAddsActivity;

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
            btnCreateAdd.setVisibility(View.INVISIBLE);
            pbFormAddsActivity.setVisibility(View.VISIBLE);

            new Handler(Looper.getMainLooper()).postDelayed(this::showMessage, 2000);
        });
        ibGetBack.setOnClickListener(view -> finish());

    }
    //--------------------------------------------------------------------------------------
    private void showMessage(){
        Toast.makeText(this, "Dados salvos!", Toast.LENGTH_SHORT).show();
        pbFormAddsActivity.setVisibility(View.GONE);
        btnCreateAdd.setVisibility(View.VISIBLE);
    }

    //Referring components
    private void referComponents(){

        btnCreateAdd = findViewById(R.id.btn_create_add);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbFormAddsActivity = findViewById(R.id.pb_form_adds_activity);

    }
    //--------------------------------------------------------------------------------------

}