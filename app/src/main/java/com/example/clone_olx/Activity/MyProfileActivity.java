package com.example.clone_olx.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;
import java.util.List;

import okhttp3.internal.cache.DiskLruCache;

public class MyProfileActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 10;

    private ImageView imgProfile;
    private ImageButton ibGetBack;
    private Button btnSave;
    private ProgressBar pbMyProfile;
    private EditText editName,editEmail;
    private MaskEditText editPhone;
    private Users user;

    private String imgPath;
    private Bitmap image;

    //Activity LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        referComponents();
        setClicks();

    }

    //Recovering user data from database
    //----------------------------------------------------------------------------------------------

    //Setting clicks on Buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {finish();});
        imgProfile.setOnClickListener(view -> {
            verifyUserPermission();
        });
        btnSave.setOnClickListener(view -> {
            validateData();
        });
    }
    //----------------------------------------------------------------------------------------------

    //Verifying if user has gaven permission to access the device gallery
    private void verifyUserPermission(){

        PermissionListener listener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openDeviceGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MyProfileActivity.this, "Permissão negada!", Toast.LENGTH_SHORT).show();
            }
        };
        showDialogPermission(listener, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE});
    }
    //----------------------------------------------------------------------------------------------

    //Opening user device gallery
    private void openDeviceGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);

    }
    //----------------------------------------------------------------------------------------------

    //Showing dialog permission whether user hasn't gaven permission to access device gallery
    private void showDialogPermission(PermissionListener permissionListener, String[] permissions){

        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permissão")
                .setDeniedMessage("Você não concedeu acesso à galeria do dispositivo. \n\n Deseja permitir? ")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Configurações")
                .setPermissions(permissions)
                .check();
    }
    //----------------------------------------------------------------------------------------------

    //Setting chosen picture by user as profile picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_GALLERY){
                Uri pathSelectedImageOnGallery = data.getData();
                imgPath = pathSelectedImageOnGallery.toString();
                if(Build.VERSION.SDK_INT < 28){
                    try{
                        image = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), pathSelectedImageOnGallery);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else{
                    ImageDecoder.Source source = ImageDecoder.createSource(getBaseContext().getContentResolver(), pathSelectedImageOnGallery);
                    try{
                        image = ImageDecoder.decodeBitmap(source);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }

                imgProfile.setImageBitmap(image);
            }
        }
    }
    //----------------------------------------------------------------------------------------------

    //Validating data provided by current user
    private void validateData(){
        String name = editName.getText().toString().trim();
        String phone = editPhone.getUnMasked().trim();

        if(!name.isEmpty()){
            if(!phone.isEmpty()){
                if(phone.length() == 11){

                    Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

                }else{
                    editPhone.requestFocus();
                    editPhone.setError("Número de telefone inválido");
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
    //----------------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){
        imgProfile = findViewById(R.id.img_profile);
        btnSave = findViewById(R.id.btn_save_my_account);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbMyProfile = findViewById(R.id.pb_my_profile);
        editEmail = findViewById(R.id.edit_email);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
    }
    //----------------------------------------------------------------------------------------------

}