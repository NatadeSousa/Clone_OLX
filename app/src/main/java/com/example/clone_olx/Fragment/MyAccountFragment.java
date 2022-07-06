package com.example.clone_olx.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clone_olx.Activity.MyAddressActivity;
import com.example.clone_olx.Activity.MyProfileActivity;
import com.example.clone_olx.R;

public class MyAccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        setClicks(view);

        return view;
    }

    //Setting clicks on options
    private void setClicks(View view){
        view.findViewById(R.id.my_profile).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyProfileActivity.class));
        });

        view.findViewById(R.id.my_address).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyAddressActivity.class));
        });
    }
    //--------------------------------------------------------------------
}