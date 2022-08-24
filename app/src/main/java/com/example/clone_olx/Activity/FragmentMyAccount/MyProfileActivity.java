package com.example.clone_olx.Activity.FragmentMyAccount;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.santalu.maskara.widget.MaskEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

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


    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        referComponents();
        recoverUserData();
        setClicks();

    }
    //----------------------------------------------------------------------------------------------

    //Recovering user data from database
    private void recoverUserData(){
        btnSave.setVisibility(View.INVISIBLE);
        pbMyProfile.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("users")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            user = snapshot.getValue(Users.class);
                            fillComponents();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pbMyProfile.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);
                        Toast.makeText(MyProfileActivity.this, "Informações não recuperadas!", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    //----------------------------------------------------------------------------------------------

    //Filling components with data that came from Database
    private void fillComponents(){
        editName.setText(user.getName());
        editPhone.setText(user.getPhone());
        editEmail.setText(user.getEmail());

        if(user.getImageUrl() != null){
            Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.loading_thin).into(imgProfile);
        }

        pbMyProfile.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
    }
    //----------------------------------------------------------------------------------------------

    //Setting clicks on Buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {finish();});
        imgProfile.setOnClickListener(view -> {
            verifyUserPermission();
        });
        btnSave.setOnClickListener(view -> {
            hideKeyboard();
            validateData();
        });
    }
    //----------------------------------------------------------------------------------------------

    //Hiding device keyboard
    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(btnSave.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //-----------------------------------------------------------------

    //Settings about profile picture

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
                    .setDeniedTitle("Permissão Negada")
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

    //----------------------------------------------------------------------------------------------

    //Validating data provided by current user
    public void validateData(){
        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if(!name.isEmpty()){
            if(!phone.isEmpty()){
                if(phone.length() == 15){
                    btnSave.setVisibility(View.INVISIBLE);
                    pbMyProfile.setVisibility(View.VISIBLE);

                    if(user == null) user = new Users();
                    user.setName(name);
                    user.setPhone(phone);

                    if(imgPath != null){
                        saveUserDataOnDatabases();
                    }else{
                        user.registerUserOnDatabase(getBaseContext(), btnSave, pbMyProfile);
                    }

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

    //Saving user data on Databases
    public void saveUserDataOnDatabases(){

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("profile_images")
                .child(FirebaseHelper.getUserIdOnDatabase() + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imgPath));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {


            String imgUrl = task.getResult().toString();
            user.setImageUrl(imgUrl);

            user.registerUserOnDatabase(getBaseContext(), btnSave,pbMyProfile);

        }).addOnFailureListener(e -> Toast.makeText(this, "Erro no upload da imagem!", Toast.LENGTH_SHORT).show()) );

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