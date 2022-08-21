package com.example.clone_olx.Activity.FragmentHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.clone_olx.Adapter.AdapterCities;
import com.example.clone_olx.Helper.CitiesList;
import com.example.clone_olx.Helper.SPFilter;
import com.example.clone_olx.Model.City;
import com.example.clone_olx.R;

public class CitiesActivity extends AppCompatActivity implements AdapterCities.OnClickListener {

    private ImageButton ibGetBack;

    private RecyclerView rvCities;
    private AdapterCities adapterCities;

    private boolean access = false;


    //Activity life cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        referComponents();

        //Verifying if user has accessed this activity through FIlterActivity
        access = getIntent().getBooleanExtra("access",false);

        setRecyclerView();
        setClicks();

    }
    //-------------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){
        rvCities.setLayoutManager(new LinearLayoutManager(this));
        rvCities.setHasFixedSize(true);
        adapterCities = new AdapterCities(CitiesList.getList(), this);
        rvCities.setAdapter(adapterCities);
    }
    //-------------------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(v -> finish());
    }
    //-------------------------------------------------------------------------------------------


    //Referring components
    private void referComponents(){

        ibGetBack = findViewById(R.id.ib_get_back);
        rvCities = findViewById(R.id.rv_cities);

    }
    //-------------------------------------------------------------------------------------------


    //Setting clicks on list items
    @Override
    public void OnClick(City city) {
        //Whether user don't click on "Todos os Estados"
        if(!city.getCityName().equals("Todos os Estados")){
            SPFilter.setFilter(this,"uf",city.getUf());
            SPFilter.setFilter(this,"cityName",city.getCityName());
            //Whether user access this activity through FilterActivity
            if(access){
                finish();
             //else
            }else{
                startActivity(new Intent(this, RegionsActivity.class));
            }
        //Whether user click on "Todos os Estados"
        }else{
            SPFilter.setFilter(this,"uf","");
            SPFilter.setFilter(this,"cityName","");
            finish();
        }
    }
    //-------------------------------------------------------------------------------------------

}