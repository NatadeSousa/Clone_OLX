package com.example.clone_olx.Model;

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
    public void registerAddressOnDatabase(String currentUserId){
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("addresses")
                .child(currentUserId)
                .setValue(this);
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
