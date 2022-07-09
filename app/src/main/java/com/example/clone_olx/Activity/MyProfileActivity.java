package com.example.clone_olx.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private Button btnSave;
    private ProgressBar pbMyProfile;
    private EditText editName,editPhone,editEmail;
    private ImageView imgProfile;
    private String imgPath;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        referComponents();
        recoverDataFromDatabase();

        setClicks();
    }

    //Recovering user data from database
    private void recoverDataFromDatabase(){

        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        reference.child("users")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                
                    }
                });

    }
    //--------------------------------------------------------------------

    //Setting clicks on Buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {finish();});
        btnSave.setOnClickListener(view -> {
            validateData();
        });
    }
    //--------------------------------------------------------------------

    //Validating data provided by current user
    private void validateData(){

        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if(!name.isEmpty()){
            if(!phone.isEmpty()){
                if(!email.isEmpty()){
                    btnSave.setVisibility(View.INVISIBLE);
                    pbMyProfile.setVisibility(View.VISIBLE);

                    if(user == null) user = new Users();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.updateUserOnDatabase(getBaseContext(),btnSave,pbMyProfile);

                }else{
                    editEmail.requestFocus();
                    editEmail.setError("Informe o seu email");
                }
            }else{
                editPhone.requestFocus();
                editPhone.setError("Informe o seu número de celular");
            }
        }else{
            editName.requestFocus();
            editName.setError("Informe o seu nome");
        }



    }
    //--------------------------------------------------------------------

    //Referring components
    private void referComponents(){
        btnSave = findViewById(R.id.btn_save_my_account);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbMyProfile = findViewById(R.id.pb_my_profile);
        editEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
    }
    //--------------------------------------------------------------------

}