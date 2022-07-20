package com.example.clone_olx.Activity.FragmentHome;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.R;

import java.util.Locale;

public class FormAddsActivity extends AppCompatActivity {

    private final int REQUEST_CATEGORY = 10;

    private CurrencyEditText editPrice;
    private ImageButton ibGetBack;
    private Button btnCreateAdd,btnCategories;
    private ProgressBar pbFormAddsActivity;
    private EditText editDescription,editTitle,editCep;
    private TextView textCharacters;

    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_adds);

        referComponents();
        setClicks();

        editPrice.setLocale(new Locale("PT","br"));

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on Buttons and Edit Texts
    private void setClicks(){

        btnCreateAdd.setOnClickListener(view -> {
            btnCreateAdd.setVisibility(View.INVISIBLE);
            pbFormAddsActivity.setVisibility(View.VISIBLE);

            new Handler(Looper.getMainLooper()).postDelayed(this::showMessage, 2000);
        });

        btnCategories.setOnClickListener(view -> {
            chooseCategory();
        });

        ibGetBack.setOnClickListener(view -> finish());

        editDescription.addTextChangedListener(watcherDescription);

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on button choose category
    private void chooseCategory(){

        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_CATEGORY);

    }
    //--------------------------------------------------------------------------------------

    private void showMessage(){
        Toast.makeText(this, "Dados salvos!", Toast.LENGTH_SHORT).show();
        pbFormAddsActivity.setVisibility(View.GONE);
        btnCreateAdd.setVisibility(View.VISIBLE);
    }

    //Verifying result of requests and applying changes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CATEGORY){
                Categories category = (Categories) data.getSerializableExtra("chosen_category");
                btnCategories.setText(category.getTitle());
                btnCategories.setTextColor(Color.rgb(73,73,73));

            }else if(true){

            }else{

            }
        }

    }
    //--------------------------------------------------------------------------------------

    //Filling textCharacters with editDescription length
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

    }
    //--------------------------------------------------------------------------------------

}