package com.example.clone_olx.Model;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Favorites {
    private List<String> favoriteAdds = new ArrayList<>();
    private String id;

    public Favorites() {
    }

    public void saveFavoriteAdd(){
        DatabaseReference favoriteAddReference = FirebaseHelper.getDatabaseReference()
                .child("favorite_adds")
                .child(FirebaseHelper.getUserIdOnDatabase());
        favoriteAddReference.setValue(this.getFavoriteAdds());
    }

    public List<String> getFavoriteAdds() {
        return favoriteAdds;
    }

    public void setFavoriteAdds(List<String> favoriteAdds) {
        this.favoriteAdds = favoriteAdds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
