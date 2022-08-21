package com.example.clone_olx.Helper;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.clone_olx.Model.City;

public class SPFilter {

    private static final String FILE_PREFERENCE = "FilePreference";

    public static void setFilter(Activity activity, String key, String value){
        SharedPreferences preferences = activity.getSharedPreferences(FILE_PREFERENCE,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static Filter getFilter(Activity activity){

        SharedPreferences preferences = activity.getSharedPreferences(FILE_PREFERENCE, 0);

        String cityName = preferences.getString("cityName", "");
        String uf = preferences.getString("uf", "");
        String region = preferences.getString("region", "");
        String category = preferences.getString("category", "");
        String search = preferences.getString("search", "");
        String ddd = preferences.getString("ddd", "");
        String minValue = preferences.getString("minValue", "");
        String maxValue = preferences.getString("maxValue", "");

        City city1 = new City();
        city1.setRegion(region);
        city1.setUf(uf);
        city1.setCityName(cityName);
        city1.setDdd(ddd);

        Filter filter = new Filter();
        filter.setCategory(category);
        filter.setCity(city1);
        filter.setSearch(search);

        if(!minValue.isEmpty()) filter.setMinValue(Integer.parseInt(minValue));
        if(!maxValue.isEmpty()) filter.setMaxValue(Integer.parseInt(maxValue));

        return filter;
    }

    public static void cleanFilter(Activity activity){
        setFilter(activity, "city","");
        setFilter(activity, "uf","");
        setFilter(activity, "region","");
        setFilter(activity, "category","");
        setFilter(activity, "search","");
        setFilter(activity, "ddd","");
    }

}
