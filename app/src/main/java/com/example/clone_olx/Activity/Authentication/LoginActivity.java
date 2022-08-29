package com.example.clone_olx.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Activity.MainActivity;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;

public class LoginActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private TextView textCreateAccount,textForgetPassword;
    private EditText editEmail,editPassword;
    private ProgressBar pbLogin;
    private Button btnLogin;
    private Users user;


    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referComponents();
        setClicks();

    }
    //-----------------------------------------------------------------


    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> finish());
        btnLogin.setOnClickListener(view -> {
            hideKeyboard();
            validateData();
        });
        textCreateAccount.setOnClickListener(view -> {
            hideKeyboard();
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
        textForgetPassword.setOnClickListener(view -> {
            hideKeyboard();
            startActivity(new Intent(this,ResetPasswordActivity.class));
        });

    }
    //-----------------------------------------------------------------

    //Hiding device keyboard
    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(btnLogin.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //-----------------------------------------------------------------


    //Validating data provided by user
    private void validateData(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(!email.isEmpty() && email.length()<25){
            if(!password.isEmpty() && password.length()<25){

                btnLogin.setVisibility(View.INVISIBLE);
                pbLogin.setVisibility(View.VISIBLE);

                signIn(email, password);

            }else{
                editPassword.requestFocus();
                editPassword.setError("Informe a sua senha");
            }
        }else{
            editEmail.requestFocus();
            editEmail.setError("Informe o seu email");
        }
    }
    //-----------------------------------------------------------------

    //Signing in user on account
    private void signIn(String email, String password){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, password
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else{
                String translatedError = FirebaseHelper.translateError(task.getException().getMessage());
                Toast.makeText(this, translatedError, Toast.LENGTH_SHORT).show();
            }

            pbLogin.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
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