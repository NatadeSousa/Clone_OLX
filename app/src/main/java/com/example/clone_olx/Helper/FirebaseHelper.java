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

    public static String translateError(String error)   {
        String msg = "Não foi possível realizar o login";

        if(error.contains("There is no user record corresponding to this identifier")){
            msg = "Este e-mail não corresponde a nenhuma conta";
        }else if(error.contains("The email address is badly formatted")){
            msg = "Formato de e-mail inválido";
        }else if(error.contains("The email address is already in use by another account")){
            msg = "Este e-mail já está em uso";
        }else if(error.contains("Password should be at least 6 characters")){
            msg = "Insira uma senha mais forte";
        }else if(error.contains("The password is invalid or the user does not have a password")){
            msg = "Senha inválida";
        }else if(error.contains("Connection to internet")){
            msg = "Sem conexão com a internet";
        }
        return msg;
    }

}
