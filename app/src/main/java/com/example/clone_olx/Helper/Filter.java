package com.example.clone_olx.Helper;

import com.example.clone_olx.Model.City;

public class Filter {
    
    private City city;
    private String category;
    private String search;
    private int minValue;
    private int maxValue;

    public Filter() {
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public String getsearch() {
        return search;
    }

    public void setsearch(String search) {
        this.search = search;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
