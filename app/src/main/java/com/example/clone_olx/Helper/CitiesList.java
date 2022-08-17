package com.example.clone_olx.Helper;

import com.example.clone_olx.Model.City;

import java.util.ArrayList;
import java.util.List;

public class CitiesList {

    public static List<City> getList(){

        List<City> citiesList = new ArrayList<>();

        citiesList.add(new City("Todos os Estados", ""));
        citiesList.add(new City("Acre", "AC"));
        citiesList.add(new City("Alagoas", "AL"));
        citiesList.add(new City("Amapá", "AP"));
        citiesList.add(new City("Amazonas", "AM"));
        citiesList.add(new City("Bahia", "BA"));
        citiesList.add(new City("Ceará", "CE"));
        citiesList.add(new City("Distrito Federal", "DF"));
        citiesList.add(new City("Espírito Santo", "ES"));
        citiesList.add(new City("Goiás", "GO"));
        citiesList.add(new City("Maranhão", "MA"));
        citiesList.add(new City("Mato Grosso", "MT"));
        citiesList.add(new City("Mato Grosso do Sul", "MS"));
        citiesList.add(new City("Minas Gerais", "MG"));
        citiesList.add(new City("Pará", "PA"));
        citiesList.add(new City("Paraíba", "PB"));
        citiesList.add(new City("Paraná", "PR"));
        citiesList.add(new City("Pernambuco", "PE"));
        citiesList.add(new City("Piauí", "PI"));
        citiesList.add(new City("Rio de Janeiro", "RJ"));
        citiesList.add(new City("Rio Grande do Norte", "RN"));
        citiesList.add(new City("Rio Grande do Sul", "RS"));
        citiesList.add(new City("Rondônia", "RO"));
        citiesList.add(new City("Roraima", "RR"));
        citiesList.add(new City("Santa Catarina", "SC"));
        citiesList.add(new City("São Paulo", "SP"));
        citiesList.add(new City("Sergipe", "SE"));
        citiesList.add(new City("Tocantins", "TO"));
        
        return citiesList;
    }

}
