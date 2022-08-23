package com.example.clone_olx.Activity.FragmentHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.clone_olx.Activity.MainActivity;
import com.example.clone_olx.Adapter.AdapterRegions;
import com.example.clone_olx.Helper.RegionsList;
import com.example.clone_olx.Helper.SPFilter;
import com.example.clone_olx.R;

public class RegionsActivity extends AppCompatActivity implements AdapterRegions.OnClickListener {

    private ImageButton ibGetBack;
    private Boolean access = false;

    private RecyclerView rvRegions;
    private AdapterRegions adapterRegions;

    //Activity life cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        referComponents();

        access = getIntent().getBooleanExtra("access",false);

        setRecyclerView();
        setClicks();

    }
    //--------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){

        rvRegions.setLayoutManager(new LinearLayoutManager(this));
        rvRegions.setHasFixedSize(true);
        adapterRegions = new AdapterRegions(RegionsList.getRegions(SPFilter.getFilter(this).getCity().getUf()), this);
        rvRegions.setAdapter(adapterRegions);

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> {
            finish();
        });

    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibGetBack = findViewById(R.id.ib_get_back);
        rvRegions = findViewById(R.id.rv_regions);

    }

    @Override
    public void OnClick(String region) {
        if(!region.equals("Todas as Regiões")){
            SPFilter.setFilter(this,"region",region);
            SPFilter.setFilter(this,"ddd", region.substring(4,6));
        }else{
            SPFilter.setFilter(this,"region", region);
            SPFilter.setFilter(this,"ddd","");
        }

        if(access){
            finish();
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    //--------------------------------------------------------------------------------------

}