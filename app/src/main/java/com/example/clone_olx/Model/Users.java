package com.example.clone_olx.Model;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Users implements Serializable {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String id;


    public Users() {
    }

    public void registerUserOnDatabase(){

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("users")
                .child(this.getId())
                .setValue(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
