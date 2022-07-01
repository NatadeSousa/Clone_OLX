package com.example.clone_olx.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editName,editPhone,editEmail,editPassword;
    private ProgressBar pbCreateAccount;
    private Button btnCreateAccount;
    private ImageButton ibGetBack;
    private Users user;


    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        referComponents();
        setClicks();

    }
    //--------------------------------------------------------------------------------

    //Registering new user on Databases
    private void createAccount(Users user){

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = task.getResult().getUser().getUid();

                user.setId(id);
                user.registerUserOnDatabase();

                pbCreateAccount.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);

                finish();
            }else{
                pbCreateAccount.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);

                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

    }
    //--------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> finish());
        btnCreateAccount.setOnClickListener(view -> {
            validateUserData();
        });

    }
    //--------------------------------------------------------------------------------

    private void validateUserData(){

        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if(!name.isEmpty() && (name.length()<=25)){
            if(!email.isEmpty() && (email.length()<=25)){
                if(!phone.isEmpty() && (phone.length()<=14)){
                    if(!password.isEmpty() && (password.length()<=25)){

                        btnCreateAccount.setVisibility(View.INVISIBLE);
                        pbCreateAccount.setVisibility(View.VISIBLE);

                        if(user == null) user = new Users();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setPhone(phone);
                        createAccount(user);


                    }else{
                        editPassword.requestFocus();
                        editPassword.setError("Digite a sua senha");
                    }
                }else{
                    editPhone.requestFocus();
                    editPhone.setError("Digite o seu número");
                }
            }else{
                editEmail.requestFocus();
                editEmail.setError("Digite o seu email");
            }
        }else{
            editName.requestFocus();
            editName.setError("Digite o seu nome");
        }

    }

    //Referring components
    private void referComponents(){

        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        pbCreateAccount = findViewById(R.id.pb_create_account_activity);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        ibGetBack = findViewById(R.id.ib_get_back);

    }
    //--------------------------------------------------------------------------------


}