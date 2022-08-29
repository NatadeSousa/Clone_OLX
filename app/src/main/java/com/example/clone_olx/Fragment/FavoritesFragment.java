package com.example.clone_olx.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private List<Adds> addsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        referComponents();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recoverFavoriteAdds();
    }

    private void recoverFavoriteAdds(){
        if(FirebaseHelper.isUserAuthenticated()){

        }else{

        }
    }

    private void referComponents(){

    }
}