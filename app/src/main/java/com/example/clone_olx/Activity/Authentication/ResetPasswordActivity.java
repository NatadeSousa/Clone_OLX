package com.example.clone_olx.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private Users user;
    private EditText editEmail;
    private ProgressBar pbResetPassword;
    private ImageButton ibGetBack;
    private Button btnResetPassword;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        referComponents();
        setClicks();


    }
    //--------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> finish());
        btnResetPassword.setOnClickListener(view -> {
            hideKeyboard();
            validateUserData();
        });

    }
    //--------------------------------------------------------------------------------

    //Hiding device keyboard
    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(btnResetPassword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //--------------------------------------------------------------------------------
    //Validating data that user typed
    private void validateUserData(){

            String email = editEmail.getText().toString().trim();

            if(!email.isEmpty() && email.length()<=25){

                btnResetPassword.setVisibility(View.INVISIBLE);
                pbResetPassword.setVisibility(View.VISIBLE);

                resetPassword(email);

            }else{
                editEmail.requestFocus();
                editEmail.setError("Informe o seu email");
            }

    }
    //--------------------------------------------------------------------------------

    //Sending link to reset password
    private void resetPassword(String email){

        FirebaseHelper.getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this, "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show();
                        new Handler(Looper.getMainLooper()).postDelayed(this::startLoginActivity,2500);
                    }else{
                        String translatedError = FirebaseHelper.translateErrorReset(task.getException().getMessage());
                        Toast.makeText(this, translatedError, Toast.LENGTH_SHORT).show();
                    }

                    pbResetPassword.setVisibility(View.GONE);
                    btnResetPassword.setVisibility(View.VISIBLE);
                });

    }
    //--------------------------------------------------------------------------------

    //Starting login activity after send the link to reset password
    private void startLoginActivity(){finish();}
    //--------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        editEmail = findViewById(R.id.edit_email);
        pbResetPassword = findViewById(R.id.pb_reset_password_activity);
        ibGetBack = findViewById(R.id.ib_get_back);
        btnResetPassword = findViewById(R.id.btn_reset_password);

    }
    //--------------------------------------------------------------------------------

}