package com.example.clone_olx.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clone_olx.R;

public class LoginActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private TextView textCreateAccount,textForgetPassword;
    private EditText editEmail,editPassword;
    private ProgressBar pbLogin;
    private Button btnLogin;


    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        ibGetBack.setOnClickListener(view -> finish());
        textCreateAccount.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
        textForgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(this,ResetPasswordActivity.class));
        });

    }
    //-----------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibGetBack = findViewById(R.id.ib_get_back);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        textCreateAccount = findViewById(R.id.text_create_account);
        textForgetPassword = findViewById(R.id.text_forget_password);
        pbLogin = findViewById(R.id.pb_login_activity);
        btnLogin = findViewById(R.id.btn_login);

    }
    //-----------------------------------------------------------------
}