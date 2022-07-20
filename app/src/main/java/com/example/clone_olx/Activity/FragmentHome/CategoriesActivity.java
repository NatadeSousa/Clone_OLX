package com.example.clone_olx.Activity.FragmentHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.clone_olx.Adapter.AdapterCategories;
import com.example.clone_olx.Helper.CategoriesList;
import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.R;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements AdapterCategories.OnClick {
    private RecyclerView rvCategories;
    private AdapterCategories adapterCategories;
    private List<Categories> categoriesList;

    private ImageButton ibGetBack;

    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        referComponents();
        setRecyclerView();
        setClicks();


    }
    //--------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){
        rvCategories.setLayoutManager(new LinearLayoutManager(this));
        rvCategories.setHasFixedSize(true);
        adapterCategories = new AdapterCategories(CategoriesList.getList(false),this);
        rvCategories.setAdapter(adapterCategories);

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

        ibGetBack.setOnClickListener(view -> finish());

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on categories
    @Override
    public void OnClickListener(Categories category) {
        Intent intent = new Intent();
        intent.putExtra("chosen_category", category);
        setResult(RESULT_OK, intent);
        finish();

    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(){

        ibGetBack = findViewById(R.id.ib_get_back);
        rvCategories = findViewById(R.id.rv_categories);

    }
    //--------------------------------------------------------------------------------------

}