package com.example.clone_olx.Activity.FragmentMyAccount;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskara.widget.MaskEditText;

public class MyAddressActivity extends AppCompatActivity {

    private Button btnSave;
    private ImageButton ibGetBack;
    private ProgressBar pbMyAddressActivity;
    private EditText editUf,editCity,editNeighborhood;
    private MaskEditText editCep;
    private Addresses address;
    //Activity Life Cycles
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);


        referComponents();
        recoverAddressFromDatabase();
        setClicks();

    }
    //--------------------------------------------------------------------

    //Recovering address from database
    private void recoverAddressFromDatabase(){
        pbMyAddressActivity.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.INVISIBLE);

        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference();
        databaseReference.child("addresses")
            .child(FirebaseHelper.getUserIdOnDatabase())
            .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot snap : snapshot.getChildren()){
                                address = snapshot.getValue(Addresses.class);
                                editCep.setText(address.getCep());
                                editUf.setText(address.getUf());
                                editNeighborhood.setText(address.getNeighborhood());
                                editCity.setText(address.getCity());
                            }
                        }
                        pbMyAddressActivity.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String currentError = error.getMessage();
                        Toast.makeText(MyAddressActivity.this, currentError, Toast.LENGTH_SHORT).show();
                        pbMyAddressActivity.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);
                    }

                });


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
                        address.registerAddressOnDatabase(FirebaseHelper.getUserIdOnDatabase(), getBaseContext(), pbMyAddressActivity, btnSave);

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
        btnSave = findViewById(R.id.btn_save_my_account);
        ibGetBack = findViewById(R.id.ib_get_back);
        pbMyAddressActivity = findViewById(R.id.pb_my_address_activity);
        editCep = findViewById(R.id.edit_cep);
        editNeighborhood = findViewById(R.id.edit_neighborhood);
        editCity = findViewById(R.id.edit_city);
        editUf  = findViewById(R.id.edit_uf);
    }
    //--------------------------------------------------------------------

}