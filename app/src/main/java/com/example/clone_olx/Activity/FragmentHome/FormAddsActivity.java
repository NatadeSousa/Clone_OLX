package com.example.clone_olx.Activity.FragmentHome;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.clone_olx.Api.CEPService;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Addresses;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.Model.Place;
import com.example.clone_olx.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormAddsActivity extends AppCompatActivity {

    private final int REQUEST_CATEGORY = 10;

    private CurrencyEditText editPrice;
    private Retrofit retrofit;
    private TedPermission permissionListener;
    private ImageButton ibGetBack;
    private Button btnCreateAdd,btnCategories;
    private ProgressBar pbFormAddsActivity;
    private EditText editDescription,editTitle,editCep;
    private TextView textCharacters, textAddress;
    private ImageView imgCamera0, imgCamera1, imgCamera2;
    private Addresses address;
    private Place place;
    private Adds add;

    private String currentPhotoPath;

    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_adds);

        referComponents();
        setRetrofit();
        recoverUserData();
        setClicks();

        editPrice.setLocale(new Locale("PT","br"));

    }
    //--------------------------------------------------------------------------------------

    //Recovering user's data from Database in order to fill component editCep
    private void recoverUserData(){
        btnCreateAdd.setVisibility(View.INVISIBLE);
        pbFormAddsActivity.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("addresses")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            address = snapshot.getValue(Addresses.class);
                            fillComponents();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FormAddsActivity.this, "Não foi possível carregar o CEP", Toast.LENGTH_SHORT).show();
                        pbFormAddsActivity.setVisibility(View.GONE);
                        btnCreateAdd.setVisibility(View.VISIBLE);
                    }
                });
    }
    //--------------------------------------------------------------------------------------
    //Filling component editCep  with data that came from Database
    private void fillComponents(){
        editCep.addTextChangedListener(watcherCep);
        editCep.setText(address.getCep());

        pbFormAddsActivity.setVisibility(View.GONE);
        btnCreateAdd.setVisibility(View.VISIBLE);
    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on Buttons and Edit Texts
    private void setClicks(){

        btnCreateAdd.setOnClickListener(view -> {
            validateData();
        });

        btnCategories.setOnClickListener(view -> {
            chooseCategory();
        });

        ibGetBack.setOnClickListener(view -> finish());

        editDescription.addTextChangedListener(watcherDescription);
        
        imgCamera0.setOnClickListener(v -> showBottomDialog(0));
        imgCamera1.setOnClickListener(v -> showBottomDialog(1));
        imgCamera2.setOnClickListener(v -> showBottomDialog(2));

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on button choose category
    private void chooseCategory(){

        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_CATEGORY);

    }
    //--------------------------------------------------------------------------------------

    //Verifying result of requests and applying changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap bitmap0, bitmap1, bitmap2;
            Uri chosenPicture = data.getData();
            String pathChosenPicture;

            if (requestCode == REQUEST_CATEGORY) {
                Categories category = (Categories) data.getSerializableExtra("chosen_category");
                btnCategories.setText(category.getTitle());
                btnCategories.setTextColor(Color.rgb(73, 73, 73));
            }else if (requestCode <= 2) {
                try {
                    pathChosenPicture = chosenPicture.toString();
                    switch (requestCode) {
                        case 0:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap0 = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenPicture);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), chosenPicture);
                                bitmap0 = ImageDecoder.decodeBitmap(source);
                            }
                            imgCamera0.setImageBitmap(bitmap0);
                            break;
                        case 1:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenPicture);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), chosenPicture);
                                bitmap1 = ImageDecoder.decodeBitmap(source);
                            }
                            imgCamera1.setImageBitmap(bitmap1);
                            break;
                        case 2:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenPicture);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), chosenPicture);
                                bitmap2 = ImageDecoder.decodeBitmap(source);
                            }
                            imgCamera2.setImageBitmap(bitmap2);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{

            }
        }
    }
    //--------------------------------------------------------------------------------------

    //Filling component textCharacters with editDescription length
    private final TextWatcher watcherDescription = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            textCharacters.setText(String.valueOf(charSequence.length()));
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
    //--------------------------------------------------------------------------------------
    //Setting listener to component editCep
    private final TextWatcher watcherCep = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String cep = charSequence.toString().replace("-","");

            if(cep.length() == 8){
                searchAddress(cep);
            }else{
                place = null;
                setAddress();
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    //--------------------------------------------------------------------------------------

    //Looking for an address corresponding to the cep, consuming the API
    private void searchAddress(String cep){
        btnCreateAdd.setVisibility(View.INVISIBLE);
        pbFormAddsActivity.setVisibility(View.VISIBLE);

       CEPService cepService = retrofit.create(CEPService.class);
       Call<Place> call = cepService.recoverCep(cep);
       call.enqueue(new Callback<Place>() {
           @Override
           public void onResponse(Call<Place> call, Response<Place> response) {
               if(response.isSuccessful()){
                   place = response.body();

                   if(place.getLocalidade() == null){
                       Toast.makeText(FormAddsActivity.this, "CEP Inválido!", Toast.LENGTH_SHORT).show();
                       pbFormAddsActivity.setVisibility(View.GONE);
                       btnCreateAdd.setVisibility(View.VISIBLE);
                   }else {
                       setAddress();
                   }
               }
           }

           @Override
           public void onFailure(Call<Place> call, Throwable t) {
               Toast.makeText(FormAddsActivity.this, "Não foi possível encontrar o endereço!", Toast.LENGTH_SHORT).show();
               pbFormAddsActivity.setVisibility(View.GONE);
               btnCreateAdd.setVisibility(View.VISIBLE);
           }
       });

    }
    //--------------------------------------------------------------------------------------

    //Filling component text_address with data that came from API
    private void setAddress(){

        if(place != null){
            String address = place.getUf() + " - " + place.getLocalidade();
            textAddress.setText(address);
        }else{
            textAddress.setText("");
        }

        pbFormAddsActivity.setVisibility(View.GONE);
        btnCreateAdd.setVisibility(View.VISIBLE);

    }
    //--------------------------------------------------------------------------------------

    //Setting library retrofit
    private void setRetrofit(){
        retrofit = new Retrofit
                .Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //--------------------------------------------------------------------------------------

    //Showing bottom dialog
    private void showBottomDialog(int requestCode){

        View modalBottomSheet = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog );
        bottomSheetDialog.setContentView(modalBottomSheet);
        bottomSheetDialog.show();
        
        
        modalBottomSheet.findViewById(R.id.btn_camera).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            verifyUserPermissionCamera(requestCode);
        });
        modalBottomSheet.findViewById(R.id.btn_gallery).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            verifyUserPermissionGallery(requestCode);
        });
        modalBottomSheet.findViewById(R.id.btn_close).setOnClickListener(view -> {
            bottomSheetDialog.findViewById(R.id.btn_close).setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
            });
        });

    }
    //--------------------------------------------------------------------------------------

    //Verifying user's permission to open device gallery or to open the device camera
    private void verifyUserPermissionGallery(int requestCode){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openDeviceGallery(requestCode);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormAddsActivity.this, "Permissão Negada!", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissionGallery(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }
    private void verifyUserPermissionCamera(int requestCode){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openDeviceCamera(requestCode);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormAddsActivity.this, "Permissão Negada!", Toast.LENGTH_SHORT).show();
            }
        };
        showDialogPermissionCamera(permissionListener, new String[]{Manifest.permission.CAMERA});

    }
    //--------------------------------------------------------------------------------------

    //Opening device gallery or opening device camera
    private void openDeviceGallery(int requestCode){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }
    private void openDeviceCamera(int requestCode){

    }

    //--------------------------------------------------------------------------------------

    //Showing dialog permission whether user hasn't given permission to open device gallery or to open device camera
    private void showDialogPermissionGallery(PermissionListener listener, String[] permissions){

        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissão Negada")
                .setDeniedMessage("Permissão negada para acessar a galeria do dispositivo. Deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Configurações")
                .setPermissions(permissions)
                .check();

    }
    private void showDialogPermissionCamera(PermissionListener listener, String[] permissions){

        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissão Negada")
                .setDeniedMessage("Permissão negada para acessar a câmera do dispositivo. Deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Configurações")
                .setPermissions(permissions)
                .check();

    }
    //--------------------------------------------------------------------------------------

    //Validating data that user has provided
    private void validateData(){

        String title = editTitle.getText().toString().trim();
        int price = (int) editPrice.getRawValue() / 100;
        String category = btnCategories.getText().toString();
        String description = editDescription.getText().toString().trim();

        if (!title.isEmpty()){
            if (price>0){
                if (!category.isEmpty()){
                    if (place != null){
                        if (place.getLocalidade() != null ) {
                            if (!description.isEmpty()) {

                                btnCreateAdd.setVisibility(View.INVISIBLE);
                                pbFormAddsActivity.setVisibility(View.VISIBLE);

                                Toast.makeText(this, "Tudo certo", Toast.LENGTH_SHORT).show();

                            } else {
                                editDescription.requestFocus();
                                editDescription.setError("Informe a descrição do anúncio!");
                            }
                        }else{
                            Toast.makeText(this, "Informe um CEP válido!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Informe um CEP válido!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Selecione uma Categoria!", Toast.LENGTH_SHORT).show();
                }
            }else{
                editPrice.requestFocus();
                editPrice.setError("Informe um preço válido!");
            }
        }else{
            editTitle.requestFocus();
            editTitle.setError("Informe o título do anúncio!");
        }

    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibGetBack = findViewById(R.id.ib_get_back);
        pbFormAddsActivity = findViewById(R.id.pb_form_adds_activity);
        btnCreateAdd = findViewById(R.id.btn_create_add);
        btnCategories = findViewById(R.id.btn_categories);
        editTitle = findViewById(R.id.edit_title);
        editPrice = findViewById(R.id.edit_price);
        editCep = findViewById(R.id.edit_cep);
        editDescription = findViewById(R.id.edit_description);
        textCharacters = findViewById(R.id.text_characters);
        textAddress = findViewById(R.id.text_address);
        imgCamera0 = findViewById(R.id.img_camera0);
        imgCamera1 = findViewById(R.id.img_camera1);
        imgCamera2 = findViewById(R.id.img_camera2);

    }
    //--------------------------------------------------------------------------------------

}