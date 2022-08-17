package com.example.clone_olx.Model;

import java.io.Serializable;

public class City implements Serializable {

    private String localidade;
    private String uf;

    public City(String localidade, String uf) {
        this.localidade = localidade;
        this.uf = uf;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
