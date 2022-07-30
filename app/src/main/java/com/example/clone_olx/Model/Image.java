package com.example.clone_olx.Model;

public class Image {
    private String pathImage;
    private int Index;

    public Image(String pathImage, int index) {
        this.pathImage = pathImage;
        Index = index;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }
}
