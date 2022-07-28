package com.example.clone_olx.Model;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Activity.FragmentHome.FormAddsActivity;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Adds implements Serializable {

    private String title;
    private String price;
    private String category;
    private String cep;
    private String description;

    public Adds() {
    }

    public void saveAddOnDatabase(Context context, ProgressBar pb, Button btn){

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("adds")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .setValue(this).addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       Toast.makeText(context, "Anúncio registrado!", Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(context, "Não foi possível registrar o anúncio!", Toast.LENGTH_SHORT).show();
                   }
                   pb.setVisibility(View.GONE);
                   btn.setVisibility(View.VISIBLE);
                });

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
