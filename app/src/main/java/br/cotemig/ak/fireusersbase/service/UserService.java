package br.cotemig.ak.fireusersbase.service;

import org.json.JSONObject;

import br.cotemig.ak.fireusersbase.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("users.json")
    Call<String> getUsers();
}
