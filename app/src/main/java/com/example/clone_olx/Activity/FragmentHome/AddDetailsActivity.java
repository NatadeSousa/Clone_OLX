package com.example.clone_olx.Activity.FragmentHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.clone_olx.Activity.Authentication.LoginActivity;
import com.example.clone_olx.Adapter.AdapterSlider;
import com.example.clone_olx.Helper.FirebaseHelper;
import com.example.clone_olx.Helper.SetMask;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.Model.Favorites;
import com.example.clone_olx.Model.Users;
import com.example.clone_olx.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class AddDetailsActivity extends AppCompatActivity {

    private TextView textTitleAdd,textRegionAdd,
            textPublicationAdd,textDescriptionAdd,
            textCategoryAdd,textCepAdd,textCityAdd,
            textPriceAdd;
    private Adds add;
    private ImageButton ibGetBack,ibPhone;
    private LikeButton ibFavorite;

    private List<String> favoriteList = new ArrayList<>();

    private SliderView sliderView;

    private Users user;
    private Favorites favorite;

    //Activity Life Cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        referComponents();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            add = (Adds) bundle.getSerializable("chosenAdd");
            setSliderView();
            fillComponents();
            recoverUserNumber();
            setIbFavorite();
            verifyFavoriteAdd();
        }

        setClicks();
    }
    //--------------------------------------------------------------------------------------

    //Filling components with add information
    private void fillComponents(){

        textTitleAdd.setText(add.getTitle());
        textPriceAdd.setText(getString(R.string.add_price,SetMask.getValue(add.getPrice())));
        textPublicationAdd.setText(getString(R.string.add_date, SetMask.getDate(add.getAddDate(),1)));
        textDescriptionAdd.setText(add.getDescription());
        textCategoryAdd.setText(add.getCategory());
        textCepAdd.setText(add.getPlace().getCep());
        textCityAdd.setText(add.getPlace().getLocalidade());
        textRegionAdd.setText(add.getPlace().getBairro());
    }
    //--------------------------------------------------------------------------------------

    //Setting SliderView
    private void setSliderView(){
        sliderView.setSliderAdapter(new AdapterSlider(add.getImagesUrl()));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
    }
    //--------------------------------------------------------------------------------------

    //Setting click on ImageButton favorite
        private void setIbFavorite(){
            ibFavorite.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if(FirebaseHelper.isUserAuthenticated()){
                        showSnackBar("","anúncio salvo com sucesso",R.drawable.ic_favorite_on_red,true);
                    }else{
                        likeButton.setLiked(false);
                        showAuthenticationAlert();
                    }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    removeFavoriteAdd();
                    showSnackBar("DESFAZER","anúncio removido",R.drawable.ic_favorite_off_white,false );
                }
            });
        }
        private void showSnackBar(String actionMsg,String msg, int icon, Boolean like){
            if(like){
                saveFavoriteAdd();
            }
            Snackbar snackbar = Snackbar.make(ibFavorite,msg,Snackbar.LENGTH_SHORT);
            snackbar.setAction(actionMsg, v -> {
                if(!like) {
                    redoAction();
                }
            });
            TextView textSnackBar = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            textSnackBar.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
            textSnackBar.setCompoundDrawablePadding(24);
            snackbar.setActionTextColor(Color.rgb(250,133,36))
                    .setTextColor(Color.rgb(255,255,255))
                    .show();
        }
        private void saveFavoriteAdd(){
            favoriteList.add(add.getId());
            favorite = new Favorites();
            favorite.setFavoriteAdds(favoriteList);
            favorite.saveFavoriteAdd();
        }
        private void removeFavoriteAdd(){
            favoriteList.remove(add.getId());
            favorite = new Favorites();
            favorite.setFavoriteAdds(favoriteList);
            favorite.saveFavoriteAdd();
        }
        private void redoAction(){
            ibFavorite.setLiked(true);
            favoriteList.add(add.getId());
            favorite = new Favorites();
            favorite.setFavoriteAdds(favoriteList);
            favorite.saveFavoriteAdd();
        }
        private void showAuthenticationAlert(){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
            alertDialog.setTitle("Entre na sua conta");
            alertDialog.setMessage("Você não está conectado à sua conta, deseja se conectar?");
            alertDialog.setNegativeButton("Não", (dialog,which) -> {
                dialog.dismiss();
            }).setPositiveButton("Sim", (dialog,which) -> {
               dialog.dismiss();
               startActivity(new Intent(this, LoginActivity.class));
               finish();
            });

            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }
        private void verifyFavoriteAdd(){
            if(FirebaseHelper.isUserAuthenticated()) {
                DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                        .child("favorite_adds")
                        .child(FirebaseHelper.getUserIdOnDatabase());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                String addId = ds.getValue(String.class);
                                favoriteList.add(addId);
                            }

                            if (favoriteList.contains(add.getId())) {
                                ibFavorite.setLiked(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    //--------------------------------------------------------------------------------------

    //Setting click on buttons
    private void setClicks(){
        ibGetBack.setOnClickListener(view -> {
            finish();
        });
        ibPhone.setOnClickListener(view -> {
            makePhoneCall();
        });
    }
    //--------------------------------------------------------------------------------------

    //Making a phone call
            //Making a phone call to the user that has registered the add
            private void makePhoneCall(){
                if(FirebaseHelper.isUserAuthenticated()) {
                    Intent call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", user.getPhone(), null));
                    startActivity(call);
                }else{
                    showAuthenticationAlert();
                }
            }
            //--------------------------------------------------------------------------------------
            //Recovering user data on Database
            private void recoverUserNumber(){
                DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                        .child("users")
                        .child(add.getUserId());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            user = snapshot.getValue(Users.class);
                        }else{
                            Toast.makeText(AddDetailsActivity.this, "Erro inesperado!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddDetailsActivity.this, FirebaseHelper.translateError(String.valueOf(error)), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            //--------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------


    //Referring components
    private void referComponents(){
        textTitleAdd = findViewById(R.id.text_title_add);
        textPriceAdd = findViewById(R.id.text_price_add);
        textPublicationAdd = findViewById(R.id.text_publication_add);
        textDescriptionAdd = findViewById(R.id.text_description_add);
        textCategoryAdd = findViewById(R.id.text_category_add);
        textCepAdd = findViewById(R.id.text_cep_add);
        textCityAdd = findViewById(R.id.text_city_add);
        textRegionAdd = findViewById(R.id.text_region_add);
        sliderView = findViewById(R.id.sliderView);
        ibGetBack = findViewById(R.id.ib_get_back);
        ibPhone = findViewById(R.id.ib_phone);
        ibFavorite = findViewById(R.id.ib_favorite);
    }
    //--------------------------------------------------------------------------------------


}