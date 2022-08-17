package com.example.clone_olx.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Activity.Authentication.LoginActivity;
import com.example.clone_olx.Activity.FragmentHome.CategoriesActivity;
import com.example.clone_olx.Activity.FragmentHome.CitiesActivity;
import com.example.clone_olx.Activity.FragmentHome.FormAddsActivity;
import com.example.clone_olx.Adapter.AdapterAdds;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterAdds.OnClickListener {

    private Button btnCreateAdd, btnCities,btnCategories,btnFilters,btnClear;
    private TextView textSearch, textInfo;
    private ProgressBar pbFragmentHome;

    private RecyclerView rvFragmentHome;
    private AdapterAdds adapterAdds;
    private List<Adds> addsList = new ArrayList<>();

    //Activity Life Cycles
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        referComponents(view);
        setRecyclerView();
        setClicks();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        fillAddsList();
    }

    //--------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){
        rvFragmentHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFragmentHome.setHasFixedSize(true);
        adapterAdds = new AdapterAdds(addsList, this);
        rvFragmentHome.setAdapter(adapterAdds);
    }
    //--------------------------------------------------------------------------------------

    //Recovering adds from database and filling addsList
    private void fillAddsList(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("public_adds");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    addsList.clear();
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Adds add = ds.getValue(Adds.class);
                        addsList.add(add);
                    }
                    pbFragmentHome.setVisibility(View.GONE);
                    textInfo.setVisibility(View.GONE);
                    Collections.reverse(addsList);
                    adapterAdds.notifyDataSetChanged();

                }else{
                    textInfo.setText("Nenhum anúncio registrado!");
                }
                pbFragmentHome.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                pbFragmentHome.setVisibility(View.GONE);
                textInfo.setText("Um erro inesperado ocorreu!");
            }
        });
    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on Buttons
    private void setClicks(){

        btnCreateAdd.setOnClickListener(v -> {
            if(FirebaseHelper.isUserAuthenticated()) {
                startActivity(new Intent(getActivity(), FormAddsActivity.class));
            }else{
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnCategories.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CategoriesActivity.class);
            intent.putExtra("allCategories", true);
            startActivity(intent);
        });

        btnCities.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), CitiesActivity.class));
        });

        btnFilters.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Filtros", Toast.LENGTH_SHORT).show();
        });

    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(View view){
        btnCreateAdd = view.findViewById(R.id.btn_create_add);
        btnCities = view.findViewById(R.id.btn_cities);
        btnCategories = view.findViewById(R.id.btn_categories);
        btnFilters = view.findViewById(R.id.btn_filters);
        btnClear = view.findViewById(R.id.btn_clear);
        pbFragmentHome = view.findViewById(R.id.pb_fragment_home);
        textInfo = view.findViewById(R.id.text_info_fragment_home);
        rvFragmentHome = view.findViewById(R.id.rv_all_adds);
    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on list items
    @Override
    public void OnClick(Adds add) {

    }
    //--------------------------------------------------------------------------------------
}