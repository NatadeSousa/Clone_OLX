package com.example.clone_olx.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.clone_olx.Activity.Authentication.LoginActivity;
import com.example.clone_olx.Activity.FragmentHome.FormAddsActivity;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.R;

public class HomeFragment extends Fragment {

    private Button btnCreateAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        referComponents(view);
        setClicks(view);

        return view;
    }


    //Setting clicks on Buttons
    private void setClicks(View view){

        btnCreateAdd.setOnClickListener(v -> {
            if(FirebaseHelper.isUserAuthenticated()) {
                startActivity(new Intent(getActivity(), FormAddsActivity.class));
            }else{
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(View view){
        btnCreateAdd = view.findViewById(R.id.btn_create_add);
    }
    //--------------------------------------------------------------------------------------
}