package com.example.clone_olx.Activity.FragmentHome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.clone_olx.R;

import java.util.Locale;

public class FilterActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private CurrencyEditText editMaxValue, editMinValue;
    private Button btnChooseCategory,btnChooseRegion,btnChooseCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        referComponents();

        editMaxValue.setLocale(new Locale("PT","br"));
        editMinValue.setLocale(new Locale("PT","br"));

        setClicks();

    }

    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {
            finish();
        });

        btnChooseCity.setOnClickListener(view -> {
            Intent intent = new Intent(this, CitiesActivity.class);
            intent.putExtra("access", true);
            startActivity(intent);
        });

    }

    private void referComponents(){
        ibGetBack = findViewById(R.id.ib_get_back);
        editMaxValue = findViewById(R.id.edit_max_value);
        editMinValue = findViewById(R.id.edit_min_value);
        btnChooseCategory = findViewById(R.id.btn_choose_category);
        btnChooseCity = findViewById(R.id.btn_choose_city);
        btnChooseRegion = findViewById(R.id.btn_choose_region);
    }
}