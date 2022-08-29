package com.example.clone_olx.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.clone_olx.Activity.MainActivity;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

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
    private List<String> imagesUrl;

    public Adds() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }


    public void saveAddOnDatabase(Activity activity, boolean newAdd) {
        DatabaseReference myAddsReference = FirebaseHelper.getDatabaseReference()
                .child("private_adds")
                .child(this.getUserId())
                .child(this.getId());
        myAddsReference.setValue(this);

        DatabaseReference publicAddsReference = FirebaseHelper.getDatabaseReference()
                .child("public_adds")
                .child(this.getId());
        publicAddsReference.setValue(this);

        if (newAdd){
            DatabaseReference privateAddDateReference = myAddsReference
                    .child("addDate");
            privateAddDateReference.setValue(ServerValue.TIMESTAMP);

            DatabaseReference publicAddDateReference = publicAddsReference
                    .child("addDate");
            publicAddDateReference.setValue(ServerValue.TIMESTAMP).addOnCompleteListener(task -> {
                activity.finish();
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("id", 2);
                activity.startActivity(intent);
            });
        }else{
            activity.finish();
        }
    }

    public void deleteAddOnDatabases(){
        DatabaseReference myAddsReference = FirebaseHelper.getDatabaseReference()
                .child("private_adds")
                .child(this.getUserId())
                .child(this.getId());
        myAddsReference.removeValue();

        DatabaseReference publicAddsReference = FirebaseHelper.getDatabaseReference()
                .child("public_adds")
                .child(this.getId());
        publicAddsReference.removeValue();

        for(int i=0;i < getImagesUrl().size(); i++) {
            StorageReference storageReference = FirebaseHelper.getStorageReference()
                    .child("images")
                    .child("add_images")
                    .child(getId())
                    .child("image" + i + ".jpeg");
            storageReference.delete();
        }
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
