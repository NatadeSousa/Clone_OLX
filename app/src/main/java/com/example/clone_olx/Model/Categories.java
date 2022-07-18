package com.example.clone_olx.Model;

import android.widget.ImageView;

import java.io.Serializable;

public class Categories implements Serializable {

    private String title;
    private int path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
}
