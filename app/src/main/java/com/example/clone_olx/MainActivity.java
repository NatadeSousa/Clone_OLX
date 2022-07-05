package com.example.clone_olx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        referComponents();
        setClicks();
        validateData();

    }
    //-----------------------------------------------------------------


    //Validating data that user typed
    private void validateData(){

    }
    //-----------------------------------------------------------------

    //Setting clicks on buttons
    private void setClicks(){

    }
    //-----------------------------------------------------------------

    //Referring components
    private void referComponents(){

    }
    //-----------------------------------------------------------------

}