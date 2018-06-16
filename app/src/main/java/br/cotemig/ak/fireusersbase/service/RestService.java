package br.cotemig.ak.fireusersbase.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestService {

    private Retrofit adapter;

    public RestService(String endPoint){

//        Gson gson = new GsonBuilder().setLenient().create();

        adapter = new Retrofit.Builder().
                baseUrl(endPoint).
                addConverterFactory(ScalarsConverterFactory.create()).
                build();

    }

    public <T> T getService(Class<T> c){
        return adapter.create(c);
    }

}