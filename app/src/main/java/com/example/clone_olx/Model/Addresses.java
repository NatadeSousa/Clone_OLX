package com.example.clone_olx.Model;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Addresses implements Serializable {

    private String cep;
    private String uf;
    private String neighborhood;
    private String city;


    public Addresses(){
    }

    public void registerAddressOnDatabase(String currentUserId, Context context, ProgressBar progressBar, Button button){
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("addresses")
                .child(currentUserId)
                .setValue(this).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "Endereço salvo!", Toast.LENGTH_SHORT).show();
                    }else{
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                });


    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
