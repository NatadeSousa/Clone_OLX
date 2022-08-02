package com.example.clone_olx.Model;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Adds implements Serializable {

    private String id;
    private String userId;
    private String title;
    private double price;
    private String category;
    private String description;
    private Place place;
    private long addDate;
    private List<String> imagesUrl = new ArrayList<>();

    public Adds() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void saveAddOnDatabase(Context context, ProgressBar pb, Button btn, boolean newAdd){

        DatabaseReference publicAddsReference = FirebaseHelper.getDatabaseReference();
        publicAddsReference.child("public_adds")
                .child(this.getId())
                .setValue(this).addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       Toast.makeText(context, "Anúncio registrado!", Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(context, "Não foi possível registrar o anúncio!", Toast.LENGTH_SHORT).show();
                   }
                });

        DatabaseReference myAddsReference = FirebaseHelper.getDatabaseReference();
        myAddsReference.child("private_adds")
                .child(this.getUserId())
                .child(this.getId())
                .setValue(this).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(context, "Anúncio registrado!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Não foi possível registrar o anúncio!", Toast.LENGTH_SHORT).show();
                    }
                });

        if(newAdd){
            DatabaseReference publicAddDateReference = publicAddsReference
                    .child("addDate");
            publicAddDateReference.setValue(ServerValue.TIMESTAMP);

            DatabaseReference privateAddDateReference = myAddsReference
                    .child("addDate");
            privateAddDateReference.setValue(ServerValue.TIMESTAMP);
        }
        pb.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
