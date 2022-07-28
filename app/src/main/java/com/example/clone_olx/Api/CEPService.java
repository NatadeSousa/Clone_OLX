package com.example.clone_olx.Api;

import com.example.clone_olx.Model.Place;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {
    @GET("{cep}/json/")
    Call<Place> recoverCep(@Path("cep") String cep);
}
