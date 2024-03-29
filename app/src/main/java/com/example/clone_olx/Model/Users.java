package com.example.clone_olx.Model;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Users {
    
    private String name;
    private String email;
    private String password;
    private String phone;
    private String id;
    private String imageUrl;


    public Users() {
    }

    public void registerUserOnDatabase(Context context, Button button, ProgressBar progressBar){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        reference.child("users")
                .child(this.getId())
                .setValue(this).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "Informações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Não possível salvar as informações!", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                });
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
