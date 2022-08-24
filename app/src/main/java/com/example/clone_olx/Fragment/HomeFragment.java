package com.example.clone_olx.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Activity.Authentication.LoginActivity;
import com.example.clone_olx.Activity.FragmentHome.CategoriesActivity;
import com.example.clone_olx.Activity.FragmentHome.CitiesActivity;
import com.example.clone_olx.Activity.FragmentHome.FilterActivity;
import com.example.clone_olx.Activity.FragmentHome.FormAddsActivity;
import com.example.clone_olx.Adapter.AdapterAdds;
import com.example.clone_olx.Helper.Filter;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Helper.SPFilter;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements AdapterAdds.OnClickListener {

    private final int REQUEST_CATEGORY = 200;

    private Button btnCreateAdd, btnCities,btnCategories,btnFilters;
    private EditText searchEditText;
    private TextView textInfo;
    private ProgressBar pbFragmentHome;
    private SearchView searchView;
    private RecyclerView rvFragmentHome;
    private AdapterAdds adapterAdds;
    private List<Adds> addsList = new ArrayList<>();

    private Filter filter = new Filter();

    //Activity Life Cycles
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        referComponents(view);
        setSearchView();
        setClicks();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        setRecyclerView();
        setFilter();
    }

    //--------------------------------------------------------------------------------------

    //Settings about home_fragment RecyclerView
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
                pbFragmentHome.setVisibility(View.VISIBLE);
                textInfo.setText("Carregando...");
                textInfo.setVisibility(View.VISIBLE);
                addsList.clear();
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

                            //Filtering by category
                            if(!filter.getCategory().isEmpty()){
                                if(!filter.getCategory().equals("Todas as Categorias")){
                                    for (Adds add : new ArrayList<>(addsList)){
                                        if(!add.getCategory().equals(filter.getCategory())){
                                            addsList.remove(add);
                                        }
                                    }
                                }
                            }

                            //Filtering by city
                            if(!filter.getCity().getUf().isEmpty()){
                                for(Adds add : new ArrayList<>(addsList)){
                                    if(!add.getPlace().getUf().equals(filter.getCity().getUf())){
                                        addsList.remove(add);
                                    }
                                }
                            }

                            //Filtering by ddd
                            if(!filter.getCity().getDdd().isEmpty()){
                                for (Adds add : new ArrayList<>(addsList)){
                                    if(!add.getPlace().getDdd().equals(filter.getCity().getDdd())){
                                        addsList.remove(add);
                                    }
                                }
                            }

                            //Filtering by search
                            if(!filter.getSearch().isEmpty()){
                                for (Adds add : new ArrayList<>(addsList)){
                                    if(!add.getTitle().toLowerCase().contains(filter.getSearch().toLowerCase())){
                                        addsList.remove(add);
                                    }
                                }
                            }

                            //Filtering by minValue and maxValue
                            if(filter.getMinValue() > 0 && filter.getMaxValue() > 0){
                                for(Adds add : new ArrayList<>(addsList)){
                                    if(add.getPrice() < filter.getMinValue() || add.getPrice() > filter.getMaxValue()){
                                        addsList.remove(add);
                                    }
                                }
                            }


                            if(addsList.size() == 0){
                                textInfo.setText("Nenhum anúncio encontrado ;(");
                                pbFragmentHome.setVisibility(View.GONE);
                            }else{
                                pbFragmentHome.setVisibility(View.GONE);
                                textInfo.setVisibility(View.GONE);
                            }

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
            startActivityForResult(intent, REQUEST_CATEGORY);
        });

        btnCities.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), CitiesActivity.class));
        });

        btnFilters.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), FilterActivity.class));
        });

    }
    //--------------------------------------------------------------------------------------

    //Settings about the text of buttons regions,categories and filters
            //Verifying where user has came back from
            private void setFilter(){
                filter = SPFilter.getFilter(getActivity());

                if(!SPFilter.getFilter(getActivity()).getCity().getCityName().isEmpty()){
                    changeRegionsButtonText();
                }else{
                    btnCities.setText("Estados");
                }
                if(!SPFilter.getFilter(getActivity()).getCategory().isEmpty()){
                    changeCategoriesButtonText();
                }else{
                    btnCategories.setText("Categorias");
                }

                fillAddsList();
            }
            //--------------------------------------------------------------------------------------
            //Verifying whether user has came back from RegionsActivity or CategoriesActivity
            private void changeRegionsButtonText(){
                btnCities.setText(SPFilter.getFilter(getActivity()).getCity().getCityName());
            }
            private void changeCategoriesButtonText(){
                btnCategories.setText(SPFilter.getFilter(getActivity()).getCategory());
            }
            //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------

    //Settings about SearchView
            //Setting SearchView
            private void setSearchView(){

                searchEditText.setTextSize(15);
                searchEditText.setTextColor(Color.rgb(49,49,49));
                searchEditText.setHintTextColor(Color.rgb(150,150,150));

                searchView.findViewById(androidx.appcompat.R.id.search_close_btn).setOnClickListener(v -> {
                    clearSearchView();
                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        SPFilter.setFilter(getActivity(),"search",query);

                        setFilter();
                        searchView.clearFocus();
                        hideKeyboard();

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
            //--------------------------------------------------------------------------------------
            //Cleaning search view
            private void clearSearchView(){
                searchEditText.setText("");
                searchView.clearFocus();
                SPFilter.setFilter(getActivity(),"search","");
                hideKeyboard();
                setRecyclerView();
                setFilter();

            }
            //--------------------------------------------------------------------------------------
            //Hiding device keyboard
            private void hideKeyboard() {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------

    //Setting intents' result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == requireActivity().RESULT_OK){
            if(requestCode == REQUEST_CATEGORY){
                Categories chosenCategory = (Categories) data.getSerializableExtra("chosen_category");
                SPFilter.setFilter(getActivity(), "category", chosenCategory.getTitle());
                setFilter();
            }
        }
    }
    //--------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(View view){
        btnCreateAdd = view.findViewById(R.id.btn_create_add);
        btnCities = view.findViewById(R.id.btn_cities);
        btnCategories = view.findViewById(R.id.btn_categories);
        btnFilters = view.findViewById(R.id.btn_filters);
        pbFragmentHome = view.findViewById(R.id.pb_fragment_home);
        textInfo = view.findViewById(R.id.text_info_fragment_home);
        rvFragmentHome = view.findViewById(R.id.rv_all_adds);
        searchView = view.findViewById(R.id.search_view);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

    }
    //--------------------------------------------------------------------------------------

    //Setting clicks on list items
    @Override
    public void OnClick(Adds add) {

    }
    //--------------------------------------------------------------------------------------
}