package com.example.clone_olx.Model;

import java.io.Serializable;

public class City implements Serializable {

    private String region;
    private String uf;
    private String cityName;
    private String ddd;

    public City(String cityName, String uf) {
        this.cityName = cityName;
        this.uf = uf;
    }

    public City() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
}
