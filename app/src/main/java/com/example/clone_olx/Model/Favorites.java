package com.example.clone_olx.Model;

import com.example.clone_olx.Helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Favorites {
    private List<String> favoriteAdds;

    public void saveFavoriteAdd(){
        DatabaseReference favoriteAddReference = FirebaseHelper.getDatabaseReference()
                .child("favorite_adds")
                .child(FirebaseHelper.getUserIdOnDatabase());
        favoriteAddReference.setValue(getFavoriteAdds());
    }

    public List<String> getFavoriteAdds() {
        return favoriteAdds;
    }

    public void setFavoriteAdds(List<String> favoriteAdds) {
        this.favoriteAdds = favoriteAdds;
    }
}
