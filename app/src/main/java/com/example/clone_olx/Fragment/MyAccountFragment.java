package com.example.clone_olx.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Activity.Authentication.LoginActivity;
import com.example.clone_olx.Activity.MainActivity;
import com.example.clone_olx.Activity.MyAddressActivity;
import com.example.clone_olx.Activity.MyProfileActivity;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyAccountFragment extends Fragment {

    private TextView textCabecalho, textAbaixoCabecalho;
    private ImageView imageUserPicture;
    private ProgressBar pbMyAccountFragment;
    private Users user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        referComponents(view);
        setTextOnHeader(view);
        if(FirebaseHelper.isUserAuthenticated()){
            recoverUserDataFromDatabase(view);
        }
        setClicks(view);


        return view;
    }

    //Recovering user data from Database
    private void recoverUserDataFromDatabase(View view){
        pbMyAccountFragment.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        reference.child("users")
                .child(FirebaseHelper.getUserIdOnDatabase())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            user = snapshot.getValue(Users.class);
                            fillComponents(view);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pbMyAccountFragment.setVisibility(View.GONE);
                    }
                });


    }
    //--------------------------------------------------------------------

    //Filling components with data that came from Database
    private void fillComponents(View view){

        textCabecalho.setText(user.getName());
        Picasso.get().load(user.getImageUrl()).into(imageUserPicture);

        pbMyAccountFragment.setVisibility(View.GONE);

    }
    //--------------------------------------------------------------------

    //Setting clicks on options
    private void setClicks(View view){
        view.findViewById(R.id.my_profile).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyProfileActivity.class));
        });

        view.findViewById(R.id.my_address).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyAddressActivity.class));
        });

        textAbaixoCabecalho.setOnClickListener(v -> {
            pbMyAccountFragment.setVisibility(View.VISIBLE);
           if(FirebaseHelper.isUserAuthenticated()){
               FirebaseHelper.getAuth().signOut();
               startActivity(new Intent(getActivity(), MainActivity.class));
           }else{
               startActivity(new Intent(getActivity(), LoginActivity.class));
           }
            pbMyAccountFragment.setVisibility(View.GONE);
        });

    }
    //--------------------------------------------------------------------

    //Setting text_view on header
    private void setTextOnHeader(View view){

        if(FirebaseHelper.isUserAuthenticated()){
            textAbaixoCabecalho.setText("Sair da conta");
        }else{
            textCabecalho.setText("Você não está conectado!");
            textAbaixoCabecalho.setText("Clique aqui");
        }


    }
    //--------------------------------------------------------------------

    //Referring components
    private void referComponents(View view){

        textCabecalho = view.findViewById(R.id.text_cabecalho);
        textAbaixoCabecalho = view.findViewById(R.id.text_abaixo_cabecalho);
        pbMyAccountFragment = view.findViewById(R.id.pb_my_account_fragment);
        imageUserPicture = view.findViewById(R.id.image_user_picture);

    }
    //--------------------------------------------------------------------

}