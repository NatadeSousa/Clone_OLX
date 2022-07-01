package com.example.clone_olx.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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
            validateUserData();
        });

    }
    //--------------------------------------------------------------------------------

    //Validating data that user typed
    private void validateUserData(){

            String email = editEmail.getText().toString().trim();

            if(!email.isEmpty() && email.length()<=25){

                btnResetPassword.setVisibility(View.INVISIBLE);
                pbResetPassword.setVisibility(View.VISIBLE);

                if(user == null) user = new Users();
                user.setEmail(email);

                resetPassword(user);

            }else{
                editEmail.requestFocus();
                editEmail.setError("Informe o seu email");
            }

    }
    //--------------------------------------------------------------------------------

    //Sending link to reset password
    private void resetPassword(Users user){

        FirebaseHelper.getAuth().sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){

                        pbResetPassword.setVisibility(View.GONE);
                        btnResetPassword.setVisibility(View.VISIBLE);

                        Toast.makeText(this, "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show();
                        new Handler(Looper.getMainLooper()).postDelayed(this::startLoginActivity,2500);
                    }else{
                        btnResetPassword.setVisibility(View.INVISIBLE);
                        pbResetPassword.setVisibility(View.VISIBLE);

                        String error = task.getException().getMessage();
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
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