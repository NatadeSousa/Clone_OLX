package com.example.clone_olx.Activity.FragmentHome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.clone_olx.Helper.Filter;
import com.example.clone_olx.Helper.SPFilter;
import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.R;

import java.util.Locale;

public class FilterActivity extends AppCompatActivity {

    private ImageButton ibGetBack;
    private CurrencyEditText editMaxValue, editMinValue;
    private Button btnChooseCategory,btnChooseRegion,btnChooseCity,btnFilter,btnClean;

    private final int REQUEST_CATEGORY = 100;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        referComponents();

        editMaxValue.setLocale(new Locale("PT","br"));
        editMinValue.setLocale(new Locale("PT","br"));

        setClicks();

    }

    @Override
    protected void onStart() {
        super.onStart();

        setFilter();

    }
    //--------------------------------------------------------------------------------------

    //Setting filter and saving data on SharedPreference
    private void setFilter(){

        Filter filter = SPFilter.getFilter(this);

        if(!filter.getCategory().isEmpty()){
            btnChooseCategory.setText(filter.getCategory());
        }else{
            btnChooseCategory.setText("Todas as Categorias");
        }

        if(!filter.getCity().getCityName().isEmpty()){
            btnChooseCity.setText(filter.getCity().getCityName());
        }else{
            btnChooseCity.setText("Todos os Estados");
            btnChooseRegion.setVisibility(View.GONE);
        }

        if(!filter.getCity().getRegion().isEmpty()){
            btnChooseRegion.setText(filter.getCity().getRegion());
            btnChooseRegion.setVisibility(View.VISIBLE);
        }else{
            btnChooseRegion.setText("Todas as Regiões");
        }
    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {
            SPFilter.cleanFilter(this);
            finish();
        });

        btnChooseCity.setOnClickListener(view -> {
            Intent intent = new Intent(this, CitiesActivity.class);
            intent.putExtra("access", true);
            startActivity(intent);
            btnChooseRegion.setVisibility(View.VISIBLE);
        });

        btnChooseRegion.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegionsActivity.class);
            intent.putExtra("access", true);
            startActivity(intent);
        });

        btnChooseCategory.setOnClickListener(view -> {
            Intent intent = new Intent(this, CategoriesActivity.class);
            startActivityForResult(intent, REQUEST_CATEGORY);
        });

        btnClean.setOnClickListener(view -> {
            SPFilter.cleanFilter(this);
            finish();
        });

        btnFilter.setOnClickListener(view -> {
            validateValues();
        });
    }
    //--------------------------------------------------------------------------------------

    //Validating minValue and MaxValue
        private void validateValues(){
            String minValue = String.valueOf(editMinValue.getRawValue() / 100);
            String maxValue = String.valueOf(editMaxValue.getRawValue() / 100);
            if(Integer.parseInt(minValue) > 0){
                if(Integer.parseInt(maxValue) > 0){
                    if(Integer.parseInt(maxValue) <= 500000){
                        if(Integer.parseInt(minValue) <= Integer.parseInt(maxValue)){
                            SPFilter.setFilter(this, "minValue", minValue);
                            SPFilter.setFilter(this, "maxValue", maxValue);
                            finish();
                        }else{
                            editMinValue.requestFocus();
                            editMinValue.setError("O valor mínimo é maior que o máximo!");
                        }
                    }else {
                        editMaxValue.requestFocus();
                        editMaxValue.setError("O valor máximo permitido é 500 mil!");
                    }
                }else{
                    editMaxValue.requestFocus();
                    editMaxValue.setError("Informe um valor máximo!");
                }
            }else{
                editMinValue.requestFocus();
                editMinValue.setError("Informe um valor mínimo!");
            }
    }
    //--------------------------------------------------------------------------------------


    //Setting intents' result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CATEGORY){
                Categories chosenCategory = (Categories) data.getExtras().getSerializable("chosen_category");
                SPFilter.setFilter(this,"category",chosenCategory.getTitle());

                setFilter();
            }
        }
    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){
        ibGetBack = findViewById(R.id.ib_get_back);
        editMaxValue = findViewById(R.id.edit_max_value);
        editMinValue = findViewById(R.id.edit_min_value);
        btnChooseCategory = findViewById(R.id.btn_choose_category);
        btnChooseCity = findViewById(R.id.btn_choose_city);
        btnChooseRegion = findViewById(R.id.btn_choose_region);
        btnFilter = findViewById(R.id.btn_filter);
        btnClean = findViewById(R.id.btn_clean);
    }
    //--------------------------------------------------------------------------------------
}