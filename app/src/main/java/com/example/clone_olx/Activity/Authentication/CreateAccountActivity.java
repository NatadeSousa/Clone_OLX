package com.example.clone_olx.Activity.Authentication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.santalu.maskara.widget.MaskEditText;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editName,editEmail,editPassword;
    private MaskEditText editPhone;
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
                if(!phone.isEmpty()){
                    if(phone.length() == 15){
                        if(!password.isEmpty() && (password.length()<=25)){

                            btnCreateAccount.setVisibility(View.INVISIBLE);
                            pbCreateAccount.setVisibility(View.VISIBLE);

                            if(user == null) user = new Users();
                            user.setName(name);
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setPhone(phone);
                            createAccount(user);


                        }else {
                            editPassword.requestFocus();
                            editPassword.setError("Digite a sua senha");
                        }
                    }else{
                        editPhone.requestFocus();
                        editPhone.setError("Informe um telefone válido");
                    }
                }else{
                    editPhone.requestFocus();
                    editPhone.setError("Digite o seu número de celular");
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
    //--------------------------------------------------------------------------------

    //Registering new user on Databases
    private void createAccount(Users user){

        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String id = task.getResult().getUser().getUid();

                user.setId(id);
                user.registerUserOnDatabase(getBaseContext(),btnCreateAccount, pbCreateAccount);

                finish();
            }else{
                String translatedError = FirebaseHelper.translateError(task.getException().getMessage());
                Toast.makeText(this, translatedError, Toast.LENGTH_SHORT).show();
                pbCreateAccount.setVisibility(View.GONE);
                btnCreateAccount.setVisibility(View.VISIBLE);
            }
        });
    }
    //--------------------------------------------------------------------------------

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