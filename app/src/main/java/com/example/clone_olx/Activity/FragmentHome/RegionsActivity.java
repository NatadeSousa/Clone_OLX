package com.example.clone_olx.Activity.FragmentHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.clone_olx.Adapter.AdapterRegions;
import com.example.clone_olx.Helper.RegionsList;
import com.example.clone_olx.R;

public class RegionsActivity extends AppCompatActivity {

    private ImageButton ibGetBack;

    private RecyclerView rvRegions;
    private AdapterRegions adapterRegions;
    private String uf = "x";

    //Activity life cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        referComponents();


        //Simplifique para:  uf = getIntent().getStringExtra("uf");
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            uf = (String) bundle.getSerializable("uf");
        }

        setRecyclerView();
        setClicks();

    }
    //--------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){

        rvRegions.setLayoutManager(new LinearLayoutManager(this));
        rvRegions.setHasFixedSize(true);
        adapterRegions = new AdapterRegions(RegionsList.getRegions(uf));
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
    //--------------------------------------------------------------------------------------

}