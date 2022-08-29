package com.example.clone_olx.Activity.FragmentHome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.clone_olx.Api.CEPService;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Helper.SetMask;
import com.example.clone_olx.Model.Addresses;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.Model.Image;
import com.example.clone_olx.Model.Place;
import com.example.clone_olx.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private ImageButton ibGetBack;
    private Button btnCreateAdd, btnCategories;
    private ProgressBar pbFormAddsActivity;
    private EditText editDescription, editTitle, editCep;
    private TextView textCharacters, textAddress, textTitleToolbar;
    private ImageView imgCamera0, imgCamera1, imgCamera2;
    private String pathChosenPicture;
    private Addresses address;
    private Place place;
    private Adds anuncio;

    private String currentPhotoPath;
    private boolean newAdd = true;

    private List<Image> imageList = new ArrayList<>();

    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_adds);

        referComponents();
        setRetrofit();
        recoverUserAddress();
        setClicks();

        editPrice.setLocale(new Locale("PT", "br"));

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            anuncio = (Adds) bundle.getSerializable("chosenAdd");

            fillAllAddComponents();
        }

    }
    //--------------------------------------------------------------------------------------

    //Recovering user's address from Database in order to fill component editCep
    private void recoverUserAddress() {
        btnCreateAdd.setVisibility(View.INVISIBLE);
        pbFormAddsActivity.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("addresses")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
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
    private void fillComponents() {
        editCep.addTextChangedListener(watcherCep);
        editCep.setText(address.getCep());

        pbFormAddsActivity.setVisibility(View.GONE);
        btnCreateAdd.setVisibility(View.VISIBLE);
    }
    //--------------------------------------------------------------------------------------

    //Filling all texts on components with adds data
    private void fillAllAddComponents(){

        textTitleToolbar.setText("Editando anúncio");
        Picasso.get().load(anuncio.getImagesUrl().get(0)).into(imgCamera0);
        Picasso.get().load(anuncio.getImagesUrl().get(1)).into(imgCamera1);
        Picasso.get().load(anuncio.getImagesUrl().get(2)).into(imgCamera2);
        editTitle.setText(anuncio.getTitle());
        editPrice.setText(SetMask.getValue(anuncio.getPrice()));
        btnCategories.setText(anuncio.getCategory());
        btnCategories.setTextColor(Color.rgb(73,73,73));
        editDescription.setText(anuncio.getDescription());
        btnCreateAdd.setText("Atualizar Anúncio");

        newAdd = false;
    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on Buttons and Edit Texts
    private void setClicks() {

        btnCreateAdd.setOnClickListener(view -> {
            hideKeyboard();
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
    private void chooseCategory() {

        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_CATEGORY);

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
            String cep = charSequence.toString().replace("-", "");

            if (cep.length() == 8) {
                searchAddress(cep);
            } else {
                place = null;
                setAddress();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    //--------------------------------------------------------------------------------------

    //Consuming API and filling components with data that came from that API
        //Looking for an address corresponding to the cep, consuming the API
        private void searchAddress(String cep) {
            btnCreateAdd.setVisibility(View.INVISIBLE);
            pbFormAddsActivity.setVisibility(View.VISIBLE);

            CEPService cepService = retrofit.create(CEPService.class);
            Call<Place> call = cepService.recoverCep(cep);
            call.enqueue(new Callback<Place>() {
                @Override
                public void onResponse(Call<Place> call, Response<Place> response) {
                    if (response.isSuccessful()) {
                        place = response.body();

                        if (place.getLocalidade() == null) {
                            Toast.makeText(FormAddsActivity.this, "CEP Inválido!", Toast.LENGTH_SHORT).show();
                            pbFormAddsActivity.setVisibility(View.GONE);
                            btnCreateAdd.setVisibility(View.VISIBLE);
                        } else {
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
        private void setAddress() {

            if (place != null) {
                String address = place.getUf() + " - " + place.getLocalidade();
                textAddress.setText(address);
            } else {
                textAddress.setText("");
            }

            pbFormAddsActivity.setVisibility(View.GONE);
            btnCreateAdd.setVisibility(View.VISIBLE);

        }
        //--------------------------------------------------------------------------------------

        //Setting library retrofit
        private void setRetrofit() {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl("https://viacep.com.br/ws/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------

    //Setting about gallery and camera
        //Showing bottom dialog
        private void showBottomDialog(int requestCode) {

                View modalBottomSheet = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
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
                modalBottomSheet.findViewById(R.id.btn_close).setOnClickListener(v -> bottomSheetDialog.dismiss());

            }
        //--------------------------------------------------------------------------------------

        //Verifying user's permission to open device gallery or to open the device camera
        private void verifyUserPermissionGallery(int requestCode) {
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
        private void verifyUserPermissionCamera(int requestCode) {

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
        private void openDeviceGallery(int requestCode) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, requestCode);
            }
        private void openDeviceCamera(int requestCode) {
            int request = 0;
            switch(requestCode){
                case 0:
                    request = 3;
                    break;
                case 1:
                    request = 4;
                    break;
                case 2:
                    request = 5;
                    break;
            }

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.clone_olx.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, request);
                }
            }
        private File createImageFile() throws IOException {
                // Create an image file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );

                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = image.getAbsolutePath();
                return image;
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

        //Setting list of images
        private void setImageList(int requestCode, String pathChosenPicture){

            int request = 0;

            switch(requestCode){
                case 0:
                case 3:
                    request = 0;
                    break;
                case 1:
                case 4:
                    request = 1;
                    break;
                case 2:
                case 5:
                    request = 2;
                    break;
            }

            Image image = new Image(pathChosenPicture, request);

            boolean thereIsAlready = false;
            if(imageList.size() > 0){
                for(int i = 0;i < imageList.size();i++){
                    if(imageList.get(i).getIndex() == request){
                        thereIsAlready = true;
                    }
                }

                if(thereIsAlready){
                    imageList.set(request,image);

                }else{
                    imageList.add(image);
                }
            }else{
                imageList.add(image);
            }


        }
        //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------

    //Validating data that user has provided
    private void validateData(){

        String title = editTitle.getText().toString().trim();
        double price = (double) editPrice.getRawValue() / 100;
        String category = btnCategories.getText().toString();
        String description = editDescription.getText().toString().trim();

        if (!title.isEmpty()){
            if (price>0 && price <= 500000){
                if (!category.isEmpty()){
                    if (place != null){
                        if (place.getLocalidade() != null ) {
                            if (!description.isEmpty()) {

                                btnCreateAdd.setVisibility(View.INVISIBLE);
                                pbFormAddsActivity.setVisibility(View.VISIBLE);

                                if(anuncio == null) anuncio = new Adds();
                                anuncio.setUserId(FirebaseHelper.getUserIdOnDatabase());
                                anuncio.setTitle(title);
                                anuncio.setPrice(price);
                                anuncio.setCategory(category);
                                anuncio.setDescription(description);
                                anuncio.setPlace(place);
                                if(newAdd){
                                    if(imageList.size() == 3){
                                        for(int i = 0; i < imageList.size(); i++) {
                                            saveAddOnDatabases(imageList.get(i), i);
                                        }
                                    }else{
                                        Toast.makeText(this, "Selecione 3 imagens para o anúncio!", Toast.LENGTH_SHORT).show();
                                        pbFormAddsActivity.setVisibility(View.GONE);
                                        btnCreateAdd.setVisibility(View.VISIBLE);
                                    }
                                }else{
                                    if(imageList.size() > 0){
                                        for(int i=0;i < imageList.size();i++){
                                            saveAddOnDatabases(imageList.get(i), i);
                                        }
                                    }else{
                                        anuncio.saveAddOnDatabase(this, false);
                                    }
                                }

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

    //Saving user data on Databases
    private void saveAddOnDatabases(Image image, int index){
        StorageReference storageReference = FirebaseHelper.getStorageReference()
                        .child("images")
                        .child("add_images")
                        .child(anuncio.getId())
                        .child("image" + index + ".jpeg");
        UploadTask uploadTask = storageReference.putFile(Uri.parse(image.getPathImage()));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

         if(newAdd) {
             anuncio.getImagesUrl().add(index, task.getResult().toString());
         }else{
             anuncio.getImagesUrl().set(image.getIndex(), task.getResult().toString());
         }

         if(imageList.size() == index + 1){
             anuncio.saveAddOnDatabase(this, newAdd);
             pbFormAddsActivity.setVisibility(View.GONE);
             btnCreateAdd.setVisibility(View.VISIBLE);
             if(newAdd == true) {
                 Toast.makeText(this, "Anúncio registrado com sucesso!", Toast.LENGTH_SHORT).show();
             }else{
                 Toast.makeText(this, "Anúncio atualizado com sucesso!", Toast.LENGTH_SHORT).show();
             }
         }

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Não foi possível salvar as imagens", Toast.LENGTH_SHORT).show();
            pbFormAddsActivity.setVisibility(View.GONE);
            btnCreateAdd.setVisibility(View.VISIBLE);
        }));

    }
    //--------------------------------------------------------------------------------------

    //Verifying result of requests and applying changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Bitmap bitmap0, bitmap1, bitmap2;

            if (requestCode == REQUEST_CATEGORY) {
                Categories category = (Categories) data.getSerializableExtra("chosen_category");
                btnCategories.setText(category.getTitle());
                btnCategories.setTextColor(Color.rgb(73, 73, 73));
            } else if (requestCode <= 2) {
                Uri chosenPicture = data.getData();
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
                    setImageList(requestCode,pathChosenPicture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                File file = new File(currentPhotoPath);
                pathChosenPicture = String.valueOf(file.toURI());

                switch(requestCode){
                    case 3:
                        imgCamera0.setImageURI(Uri.fromFile(file));
                        break;
                    case 4:
                        imgCamera1.setImageURI(Uri.fromFile(file));
                        break;
                    case 5:
                        imgCamera2.setImageURI(Uri.fromFile(file));
                        break;
                }
                setImageList(requestCode, pathChosenPicture);
            }
        }
    }
    //--------------------------------------------------------------------------------------

    //Hiding device keyboard
    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(btnCreateAdd.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
        textTitleToolbar = findViewById(R.id.text_title_toolbar_form_adds);
        imgCamera0 = findViewById(R.id.img_camera0);
        imgCamera1 = findViewById(R.id.img_camera1);
        imgCamera2 = findViewById(R.id.img_camera2);

    }
    //--------------------------------------------------------------------------------------

}