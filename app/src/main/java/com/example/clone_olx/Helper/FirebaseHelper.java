package com.example.clone_olx.Helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {

    private static FirebaseAuth auth;
    private static DatabaseReference databaseReference;
    private static StorageReference storageReference;


    public static FirebaseAuth getAuth(){

        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
    public static DatabaseReference getDatabaseReference(){

        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }
    public static StorageReference getStorageReference(){

        if(storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }
    public static String getUserIdOnDatabase(){

        return getAuth().getUid();

    }
    public static boolean isUserAuthenticated(){

        return getAuth().getCurrentUser() != null;

    }


}
