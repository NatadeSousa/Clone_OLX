package com.example.clone_olx.Fragment;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clone_olx.Activity.FragmentHome.FormAddsActivity;
import com.example.clone_olx.Adapter.AdapterAdds;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyAddsFragment extends Fragment implements AdapterAdds.OnClickListener {

    private ProgressBar pbMyAdds;
    private TextView textInfo;
    private AdapterAdds adapterAdds;
    private List<Adds> addsList = new ArrayList<>();
    private SwipeableRecyclerView rvMyAdds;
    private AdapterAdds.OnClickListener onClickListener;


    //Fragment Life Cycles
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_adds, container, false);

        referComponents(view);
        setRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recoverAddsOnDatabase();
    }

    //----------------------------------------------------------------------------------------------

    //Recovering private adds on Database
    private void recoverAddsOnDatabase(){
        if(FirebaseHelper.isUserAuthenticated()){
            DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                    .child("private_adds")
                    .child(FirebaseHelper.getUserIdOnDatabase());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        addsList.clear();
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Adds add = snap.getValue(Adds.class);
                            addsList.add(add);

                        }

                        Collections.reverse(addsList);
                        adapterAdds.notifyDataSetChanged();
                        textInfo.setText("");
                    }else{
                        textInfo.setText("Nenhum anúncio registrado!");
                    }
                    pbMyAdds.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Não foi possível recuperar seus anúncios", Toast.LENGTH_SHORT).show();
                    pbMyAdds.setVisibility(View.GONE);
                    textInfo.setText("Tente novamente mais tarde!");
                }
            });
        }else{
            pbMyAdds.setVisibility(View.GONE);
            textInfo.setText("Você não está conectado à sua conta!");
        }
    }
    //----------------------------------------------------------------------------------------------

    //Setting recycler view
    private void setRecyclerView(){
        rvMyAdds.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMyAdds.setHasFixedSize(true);
        adapterAdds = new AdapterAdds(addsList, this);
        rvMyAdds.setAdapter(adapterAdds);

        rvMyAdds.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
                showDialogEdit(addsList.get(position));
            }

            @Override
            public void onSwipedRight(int position) {
                showDialogExclude(addsList.get(position));
            }
        });

    }
    //----------------------------------------------------------------------------------------------

    //Setting alert dialogs to edit or exclude adds
    private void showDialogEdit(Adds add){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(),R.style.AlertDialogCustom));
        alertDialog.setTitle("Edição de Anúncio");
        alertDialog.setMessage("Você deseja editar o anúncio escolhido? ");
        alertDialog.setNegativeButton("Não", (dialog, which) -> {
            dialog.dismiss();
            adapterAdds.notifyDataSetChanged();
        }).setPositiveButton("Sim", (dialog, which) -> {
            Intent intent = new Intent(requireActivity(), FormAddsActivity.class);
            intent.putExtra("chosenAdd", add);
            startActivity(intent);

            adapterAdds.notifyDataSetChanged();
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }
    private void showDialogExclude(Adds add){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.AlertDialogCustom));
        alertDialog.setTitle("Exclusão de Anúncio");
        alertDialog.setMessage("Deseja excluir esse anúncio permanentemente? ");
        alertDialog.setNegativeButton("Não", (dialog, which) -> {
           dialog.dismiss();
           adapterAdds.notifyDataSetChanged();
        }).setPositiveButton("Sim", (dialog, which) -> {
            addsList.remove(add);
            add.deleteAddOnDatabases();

            adapterAdds.notifyDataSetChanged();
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }
    //----------------------------------------------------------------------------------------------

    //Setting clicks on list items
    @Override
    public void OnClick(Adds add) {

    }
    //----------------------------------------------------------------------------------------------

    //Referring components
    private void referComponents(View view){
        pbMyAdds = view.findViewById(R.id.pb_my_adds);
        textInfo = view.findViewById(R.id.text_info);

        rvMyAdds = view.findViewById(R.id.rv_my_adds);
    }
    //----------------------------------------------------------------------------------------------

}