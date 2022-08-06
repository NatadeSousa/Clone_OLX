package com.example.clone_olx.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Adapter.AdapterAdds;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyAddsFragment extends Fragment implements AdapterAdds.OnClickListener {

    private SwipeableRecyclerView rvMyAdds;
    private AdapterAdds adapterAdds;
    private ProgressBar pbMyAdds;
    private TextView textInfo;

    private List<Adds> addsList = new ArrayList<>();

    //Fragment Life Cycles
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_adds, container, false);

        referComponents(view);
        setRvMyAdds();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        recoverAddsOnDatabase();
    }
    //----------------------------------------------------------------------------------------------

    //Recovering private adds on Database
    private void recoverAddsOnDatabase() {
        if (FirebaseHelper.isUserAuthenticated()) {
            DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                    .child("private_adds")
                    .child(FirebaseHelper.getUserIdOnDatabase());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        textInfo.setText("");

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Adds add = snap.getValue(Adds.class);
                            addsList.add(add);
                        }
                    } else {
                        textInfo.setText("Nenhum anúncio registrado");
                    }
                    Collections.reverse(addsList);
                    adapterAdds.notifyDataSetChanged();
                    pbMyAdds.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Erro inesperado!", Toast.LENGTH_SHORT).show();
                    textInfo.setText("Tente novamente mais tarde!");
                    pbMyAdds.setVisibility(View.GONE);
                }
            });
        }else{
            textInfo.setText("Você não está conectado!");
            pbMyAdds.setVisibility(View.GONE);
        }
    }
    //----------------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRvMyAdds(){
        rvMyAdds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMyAdds.setHasFixedSize(true);
        adapterAdds = new AdapterAdds(addsList, this);
        rvMyAdds.setAdapter(adapterAdds);
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void OnClick(Adds add) {

    }

    //Referring components
    private void referComponents(View view){
        rvMyAdds = view.findViewById(R.id.rv_my_adds);
        pbMyAdds = view.findViewById(R.id.pb_my_adds);
        textInfo = view.findViewById(R.id.text_info);
    }

    //----------------------------------------------------------------------------------------------

}