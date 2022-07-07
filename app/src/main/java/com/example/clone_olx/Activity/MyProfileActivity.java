package com.example.clone_olx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clone_olx.R;

public class MyProfileActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private Button btnSave;
    private ProgressBar pbMyProfile;
    private TextView textEmail;
    private EditText editName,editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        referComponents();
        setClicks();
    }

    //Setting clicks on Buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {finish();});
        btnSave.setOnClickListener(view -> {
            validateData();
        });
    }
    //--------------------------------------------------------------------

    private void validateData(){

        btnSave.setVisibility(View.INVISIBLE);
        pbMyProfile.setVisibility(View.VISIBLE);

    }

    //Referring components
    private void referComponents(){
        btnSave = findViewById(R.id.btn_save_my_account);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbMyProfile = findViewById(R.id.pb_my_profile);
        textEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
    }
    //--------------------------------------------------------------------

}