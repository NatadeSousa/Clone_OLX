package com.example.clone_olx.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Addresses;
import com.example.clone_olx.R;

public class MyAddressActivity extends AppCompatActivity {

    private Button btnSave;
    private ImageButton ibGetBack;
    private ProgressBar pbMyAddressActivity;
    private EditText editCep,editUf,editCity,editNeighborhood;
    private Addresses address;

    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        referComponents();
        setClicks();

    }
    //--------------------------------------------------------------------

    //Setting clicks on Buttons
    private void setClicks(){
        btnSave.setOnClickListener(view -> {
            validateData();
        });

        ibGetBack.setOnClickListener(view -> finish());
    }
    //--------------------------------------------------------------------

    //Validating data provided by current address
    private void validateData(){

        String cep = editCep.getText().toString().trim();
        String uf = editUf.getText().toString().trim();
        String neighborhood = editNeighborhood.getText().toString().trim();
        String city = editCity.getText().toString().trim();

        if(!cep.isEmpty()){
            if(!uf.isEmpty()){
                if(!city.isEmpty()){
                    if(!neighborhood.isEmpty()){

                        btnSave.setVisibility(View.INVISIBLE);
                        pbMyAddressActivity.setVisibility(View.VISIBLE);

                        if(address == null) address = new Addresses();
                        address.setCep(cep);
                        address.setUf(uf);
                        address.setNeighborhood(neighborhood);
                        address.setCity(city);
                        address.registerAddressOnDatabase(FirebaseHelper.getUserIdOnDatabase());

                        pbMyAddressActivity.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);

                        Toast.makeText(this, "Tudo certo", Toast.LENGTH_SHORT).show();


                    }else{
                        editNeighborhood.requestFocus();
                        editNeighborhood.setError("Informe o seu bairro");
                    }
                }else{
                    editCity.requestFocus();
                    editCity.setError("Informe sua cidade");
                }
            }else{
                editUf.requestFocus();
                editUf.setError("Informe a UF");
            }
        }else{
            editCep.requestFocus();
            editCep.setError("Informe o CEP");
        }

    }
    //--------------------------------------------------------------------

    //Referring components
    private void referComponents(){
        btnSave = findViewById(R.id.btn_save);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbMyAddressActivity = findViewById(R.id.pb_my_address_activity);
        editCep = findViewById(R.id.edit_cep);
        editNeighborhood = findViewById(R.id.edit_neighborhood);
        editCity = findViewById(R.id.edit_city);
        editUf  = findViewById(R.id.edit_uf);
    }
    //--------------------------------------------------------------------

}