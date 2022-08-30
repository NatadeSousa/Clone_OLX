package com.example.clone_olx.Fragment;

import static android.view.View.GONE;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Activity.Authentication.LoginActivity;
import com.example.clone_olx.Activity.FragmentHome.AddDetailsActivity;
import com.example.clone_olx.Adapter.AdapterAdds;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.Model.Favorites;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesFragment extends Fragment implements AdapterAdds.OnClickListener {

    private ProgressBar pbFavoriteAdds;
    private TextView textInfo;

    private List<Adds> addsList = new ArrayList<>();
    private List<String> addIdList = new ArrayList<>();
    private AdapterAdds adapterAdds;
    private SwipeableRecyclerView rvFavoriteAdds;

    //Fragment life cycles
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        referComponents(view);
        setRecyclerView();

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        recoverFavoriteAdds();
    }
    //----------------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){
        rvFavoriteAdds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavoriteAdds.setHasFixedSize(true);
        adapterAdds = new AdapterAdds(addsList, this);
        rvFavoriteAdds.setAdapter(adapterAdds);

        rvFavoriteAdds.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                showDialogExclude(addsList.get(position));
                adapterAdds.notifyItemRemoved(position);
                adapterAdds.notifyDataSetChanged();
                
            }

            @Override
            public void onSwipedRight(int position) {
                showDialogExclude(addsList.get(position));
                adapterAdds.notifyItemRemoved(position);
                adapterAdds.notifyDataSetChanged();

            }
        });
    }
    //----------------------------------------------------------------------------------------------

    //Showing alert dialog whether user want to remove and add from favorite adds list
    private void showDialogExclude(Adds add){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.AlertDialogCustom));
        alertDialog.setTitle("Excluir dos Favoritos");
        alertDialog.setMessage("Deseja excluir esse anúncio da sua lista de favoritos? ");
        alertDialog.setNegativeButton("Não", (dialog, which) -> {
            dialog.dismiss();
            adapterAdds.notifyDataSetChanged();
        }).setPositiveButton("Sim", (dialog, which) -> {
            addsList.remove(add);
            addIdList.remove(add.getId());
            Favorites favorite = new Favorites();
            favorite.setFavoriteAdds(addIdList);
            favorite.saveFavoriteAdd();
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }
    //----------------------------------------------------------------------------------------------

    //Recovering favorite adds on Database
    private void recoverFavoriteAdds(){
        if(FirebaseHelper.isUserAuthenticated()){
            DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                    .child("favorite_adds")
                    .child(FirebaseHelper.getUserIdOnDatabase());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    addsList.clear();
                    addIdList.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot ds : snapshot.getChildren()){
                            String addId = ds.getValue(String.class);
                            addIdList.add(addId);
                        }
                        recoverAddsUsingId();
                    }else{
                        textInfo.setText("Nenhum anúncio favorito selecionado ;(");
                        pbFavoriteAdds.setVisibility(GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    textInfo.setText("Não foi possível recuperar seus anúncios ;(");
                    pbFavoriteAdds.setVisibility(GONE);
                    Toast.makeText(requireActivity(), "Um erro inesperado ocorreu", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            pbFavoriteAdds.setVisibility(GONE);
            textInfo.setText(R.string.not_connected);
            showAuthenticationAlert();
        }
    }
    //----------------------------------------------------------------------------------------------
    //Recovering adds on Database based on Add Id
    private void recoverAddsUsingId(){
        for(int i=0;i<addIdList.size();i++){
            DatabaseReference publicAddsReference = FirebaseHelper.getDatabaseReference()
                    .child("public_adds")
                    .child(addIdList.get(i));
            publicAddsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Adds add = snapshot.getValue(Adds.class);
                    addsList.add(add);
                    if(addsList.size() == addIdList.size()){
                        textInfo.setVisibility(GONE);
                        Collections.reverse(addsList);
                        adapterAdds.notifyDataSetChanged();
                        pbFavoriteAdds.setVisibility(GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    //----------------------------------------------------------------------------------------------

    //Showing alert dialog whether user isn't authenticated
    private void showAuthenticationAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(requireActivity(),R.style.AlertDialogCustom));
        alertDialog.setTitle("Entre na sua conta");
        alertDialog.setTitle("Você não está conectado à sua conta. Deseja se conectar?");
        alertDialog.setNegativeButton("Não", (dialog,which) -> {
            dialog.dismiss();
        }).setPositiveButton("Sim", (dialog,which) -> {
            startActivity(new Intent(requireContext(), LoginActivity.class));
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
    //----------------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(View view){
        rvFavoriteAdds = view.findViewById(R.id.rv_favorite_adds);
        textInfo = view.findViewById(R.id.text_info);
        pbFavoriteAdds = view.findViewById(R.id.pb_favorite_adds);
    }
    //----------------------------------------------------------------------------------------------

    //Setting click on one of the favorite adds
    @Override
    public void OnClick(Adds add) {
        Intent intent = new Intent(requireContext(), AddDetailsActivity.class);
        intent.putExtra("chosenAdd", add);
        startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------

}